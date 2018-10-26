package uk.ac.sheffield.nlp.fever.annotation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import uk.ac.sheffield.nlp.fever.annotation.db.FEVERSessionFactory;
import uk.ac.sheffield.nlp.fever.annotation.messaging.messagetypes.AnnotationRequest;
import uk.ac.sheffield.nlp.fever.annotation.messaging.rabbitmq.RabbitMQMessageSender;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.GsonSerializer;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.Serializer;
import uk.ac.sheffield.nlp.fever.annotation.model.CandidateEvidence;
import uk.ac.sheffield.nlp.fever.annotation.model.Claim;
import uk.ac.sheffield.nlp.fever.annotation.model.Label;
import uk.ac.sheffield.nlp.fever.annotation.model.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class LoadPageUpdate {

    static String allIds = "";

    public static void main(String[] args) throws IOException, TimeoutException {

        int i = 0;

        String[] ids = Arrays.stream(allIds.split("\n")).map(String::trim).toArray(String[]::new);

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] bits = line.split("\t");

                String id = bits[0];
                String page = bits[1];

                if (!Arrays.asList(ids).contains(id)) {
                    continue;
                }

                System.out.println("UPDATE claim SET originalPage = \"" + page + "\" WHERE originalId = " + id + " LIMIT 1;");

            }
        }


    }

    private static void x() throws IOException, TimeoutException {

        /*

        Serializer<AnnotationRequest> ser = new GsonSerializer<>(AnnotationRequest.class);

        RabbitMQMessageSender<AnnotationRequest> sender1 = new RabbitMQMessageSender<>(ser, "annotation_task_1");
        RabbitMQMessageSender<AnnotationRequest> sender2 = new RabbitMQMessageSender<>(ser, "annotation_task_2");
        RabbitMQMessageSender<AnnotationRequest> sender3 = new RabbitMQMessageSender<>(ser, "annotation_task_3");



        AnnotationRequest req = new AnnotationRequest();
        req.setTaskId(1L);

        sender1.send(req);
        sender2.send(req);
        sender3.send(req);


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
        */

    }


}
