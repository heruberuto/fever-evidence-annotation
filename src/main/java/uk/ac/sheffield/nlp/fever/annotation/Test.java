package uk.ac.sheffield.nlp.fever.annotation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uk.ac.sheffield.nlp.fever.annotation.dao.*;
import uk.ac.sheffield.nlp.fever.annotation.db.FEVERSessionFactory;
import uk.ac.sheffield.nlp.fever.annotation.messaging.messagetypes.AnnotationRequest;
import uk.ac.sheffield.nlp.fever.annotation.messaging.rabbitmq.RabbitMQMessageSender;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.GsonSerializer;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.Serializer;
import uk.ac.sheffield.nlp.fever.annotation.model.*;

import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class Test {

    public static void main(String[] args) {


        System.out.println(Instant.now());


        SessionFactory sf = FEVERSessionFactory.getInstance();

        //PageDAOImpl dao = new PageDAOImpl(sf);


       // Page p = new Page();
      //  p.setPage("text");
       // p.setText("0\tline0\t\n1\tline1\t\n2\tline2\t\n3\tline3\t\n");
     //   dao.save(p);


        x();

    }

    private static void x(){


        Serializer<AnnotationRequest> ser = new GsonSerializer<>(AnnotationRequest.class);
        try {
            RabbitMQMessageSender<AnnotationRequest> sender1 = new RabbitMQMessageSender<>(ser, "annotation_task_1");
            RabbitMQMessageSender<AnnotationRequest> sender2 = new RabbitMQMessageSender<>(ser, "annotation_task_2");
            RabbitMQMessageSender<AnnotationRequest> sender3 = new RabbitMQMessageSender<>(ser, "annotation_task_3");


            AnnotationRequest req1 = new AnnotationRequest();
            req1.setTaskId(1L);

            AnnotationRequest req2 = new AnnotationRequest();
            req2.setTaskId(2L);

            sender1.send(req1);
            sender1.send(req2);


            sender2.send(req1);
            sender2.send(req2);


            sender3.send(req1);
            sender3.send(req2);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


        SessionFactory sf = FEVERSessionFactory.getInstance();


        Annotator anno = new Annotator();
        anno.setAuth("hello");
        anno.setCreatedDate(Instant.now());


        Claim claim = new Claim();

        claim.setClaimText("this is claim text");
        claim.setOriginalId(1234L);



        CandidateEvidence ce1 = new CandidateEvidence();
        ce1.setLine(1);
        ce1.setPage("text");

        CandidateEvidence ce2 = new CandidateEvidence();
        ce2.setLine(2);
        ce2.setPage("text");


        TaskDAO td = new TaskDAOImpl(sf);



        Task t = new Task();
        t.setClaim(claim);
        t.setOriginalLabel(Label.SUPPORTED);

        ce1.setTask(t);




        Task t2 = new Task();
        t2.setClaim(claim);
        t2.setOriginalLabel(Label.SUPPORTED);

        ce2.setTask(t2);


        td.save(anno);
        td.save(claim);
        td.save(t);
        td.save(t2);
        td.save(ce1);

        FEVERSessionFactory.getInstance().close();

    }


}
