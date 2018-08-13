package uk.ac.sheffield.nlp.fever.annotation.controller;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import uk.ac.sheffield.nlp.fever.annotation.dao.*;
import uk.ac.sheffield.nlp.fever.annotation.messaging.messagetypes.AnnotationRequest;
import uk.ac.sheffield.nlp.fever.annotation.messaging.rabbitmq.AmqSetup;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.GsonSerializer;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.Serializer;
import uk.ac.sheffield.nlp.fever.annotation.model.*;
import uk.ac.sheffield.nlp.fever.annotation.viewmodel.AnnotationTask;
import uk.ac.sheffield.nlp.fever.annotation.viewmodel.AnnotationViewModel;
import uk.ac.sheffield.nlp.fever.annotation.viewmodel.AnnotatorCountViewModel;
import uk.ac.sheffield.nlp.fever.annotation.viewmodel.ResolvedCandidateEvidence;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;


@Controller
@RequestMapping(value="/task")
public class TaskController {

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Task not found")
    public class TaskNotFound extends RuntimeException {
    }

    @Autowired
    private AnnotationDAO annotations;

    @Autowired
    private TaskDAO tasks;

    @Autowired
    private AnnotatorDAO annotators;

    @Autowired
    private AnnotationAssignmentDAO annotationAssignments;

    @Autowired
    private PageDAO pages;

    @Autowired
    private EvidenceDAO evidenceDAO;



    private Channel[] channels = new Channel[3];
    private Serializer<AnnotationRequest> requestSerializer;
    public TaskController() {

        requestSerializer = new GsonSerializer<>(AnnotationRequest.class);

        AmqSetup<AnnotationRequest> setup1 = new AmqSetup<>(requestSerializer, "annotation_task_1");
        AmqSetup<AnnotationRequest> setup2 = new AmqSetup<>(requestSerializer, "annotation_task_2");
        AmqSetup<AnnotationRequest> setup3 = new AmqSetup<>(requestSerializer, "annotation_task_3");


        setup1.run(); //Don't run this in a thread
        setup2.run(); //Don't run this in a thread
        setup3.run(); //Don't run this in a thread

        channels[0] = setup1.getChannel();
        channels[1] = setup2.getChannel();
        channels[2] = setup3.getChannel();



    }



    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public void putAnnotation(@PathVariable(name="id") long taskId,
                              @RequestBody AnnotationViewModel annotationVM, final HttpServletRequest request) {

        Annotator annotator = annotators.get((Long)request.getSession().getAttribute("annotator"));
        Task task = tasks.get(taskId);

        if(task == null) {
            throw new TaskNotFound();
        }


        AnnotationAssignment userAssignment = annotationAssignments.checkActive(task,annotator);
        if(userAssignment == null) {
            throw new TaskNotFound();
        }



        Annotation annotation = new Annotation();
        annotation.setCreatedDate(Instant.now());
        annotation.setAnnotator(annotator);
        annotation.setTask(task);
        annotation.setAssignment(userAssignment);

        annotation.setIndividualEvidence(evidenceDAO.getEvidence(task,annotationVM.getIndividualEvidence()));
        annotation.setPartialEvidence(evidenceDAO.getEvidence(task,annotationVM.getPartialEvidence()));
        annotation.setLabel(annotationVM.getLabel());


        annotations.save(annotation);
        userAssignment.setAnnotation(annotation);
        annotationAssignments.update(userAssignment);

    }


    @RequestMapping(value="/skip/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public void putSkipAnnotation(@PathVariable(name="id") long taskId,
                              final HttpServletRequest request) {

        Annotator annotator = annotators.get((Long)request.getSession().getAttribute("annotator"));
        Task task = tasks.get(taskId);

        if(task == null) {
            throw new TaskNotFound();
        }


        AnnotationAssignment userAssignment = annotationAssignments.checkActive(task,annotator);
        if(userAssignment == null) {
            throw new TaskNotFound();
        }


        userAssignment.setSkipped(true);
        annotationAssignments.update(userAssignment);

    }


    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseBody
    public AnnotationTask get(@PathVariable(name="id") long id,
                              final HttpServletRequest req) {

        Annotator annotator = annotators.get((Long)req.getSession().getAttribute("annotator"));
        Task task = tasks.get(id);

        if(task == null) {
            throw new TaskNotFound();
        }


        AnnotationAssignment userAssignment = annotationAssignments.checkActive(task,annotator);
        if(userAssignment == null) {
            throw new TaskNotFound();
        }

        Set<ResolvedCandidateEvidence> resolvedEvidence = new HashSet<>();

        for(CandidateEvidence evidence : task.getEvidence()) {
            ResolvedCandidateEvidence resolved = new ResolvedCandidateEvidence();

            resolved.setId(evidence.getId());
            resolved.setLineNumber(evidence.getLine());
            resolved.setPage(evidence.getPage());
            resolved.setEvidenceText(pages.getLine(evidence.getPage(),(int)evidence.getLine()));

            resolvedEvidence.add(resolved);

        }



        AnnotationTask annotationTask = new AnnotationTask();
        annotationTask.setClaimId(task.getClaim().getId());
        annotationTask.setClaimText(task.getClaim().getClaimText());
        annotationTask.setEvidence(resolvedEvidence);
        annotationTask.setOriginalPage(task.getClaim().getOriginalPage());



        return annotationTask;

    }

    @RequestMapping(value="/next")
    @ResponseBody
    public AnnotationRequest next(final HttpServletRequest wreq){
        Annotator anno = annotators.get((Long)wreq.getSession().getAttribute("annotator"));
        AnnotationRequest req = new AnnotationRequest();

        AnnotationAssignment check = annotationAssignments.getActive(anno);
        if (check == null) {
            AnnotationAssignment assignment = getAssignmentFromQueues(anno);

            if (assignment == null) {
                throw new TaskNotFound();
            }

            req.setTaskId(assignment.getTask().getId());


        } else {
            req.setTaskId(check.getTask().getId());
            check.setExpiresDate(
                    LocalDateTime
                            .from(Instant.now().atOffset(ZoneOffset.UTC))
                            .plusDays(1)
                            .toInstant(ZoneOffset.UTC));



            annotationAssignments.update(check);
        }


        return req;
    }


    @RequestMapping(value="/count", method=RequestMethod.GET)
    @ResponseBody
    public AnnotatorCountViewModel getCount(final HttpServletRequest request) {


        Annotator annotator = annotators.get((Long)request.getSession().getAttribute("annotator"));
        long count = annotators.getAnnotatorCount(annotator);
        return new AnnotatorCountViewModel(count);

    }


    public boolean checkRequest(AnnotationRequest request, Annotator annotator) {

        long taskId = request.getTaskId();
        Task task = tasks.get(taskId);

        return annotationAssignments.check(task, annotator);

    }

    public AnnotationAssignment getAssignmentFromQueue(Annotator annotator, int q) {
        GetResponse response = null;
        try {
            response = channels[q-1].basicGet("annotation_task_"+q, false);

            if(response == null) {
                return null;
            }

            AnnotationRequest request = this.requestSerializer.deserialize(response.getBody());

            if(request == null) {
                return null;
            }


            if(!checkRequest(request,annotator)) {
                AnnotationAssignment assignment = new AnnotationAssignment();
                Task t = tasks.get(request.getTaskId());

                assignment.setTask(t);
                assignment.setCreatedDate(Instant.now());
                assignment.setExpiresDate(
                        LocalDateTime
                                .from(Instant.now().atOffset(ZoneOffset.UTC))
                                .plusDays(1)
                                .toInstant(ZoneOffset.UTC));

                assignment.setAnnotator(annotator);
                assignment.setSkipped(false);


                annotationAssignments.save(assignment);

                channels[q-1].basicAck(response.getEnvelope().getDeliveryTag(), false);
                return assignment;
            } else {
                channels[q-1].basicNack(response.getEnvelope().getDeliveryTag(), false, true);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public AnnotationAssignment getAssignmentFromQueues(Annotator annotator) {

        List<Integer> choices = Arrays.asList(1,2,3);

        Collections.shuffle(choices,new Random());

        for(int i : choices) {
            AnnotationAssignment req = getAssignmentFromQueue(annotator, i);
            if (req!=null) {

                return req;
            }
        }


        return null;

    }


}
