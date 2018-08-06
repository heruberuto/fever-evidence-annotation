package uk.ac.sheffield.nlp.fever.annotation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import uk.ac.sheffield.nlp.fever.annotation.dao.TaskDAO;
import uk.ac.sheffield.nlp.fever.annotation.dao.TaskDAOImpl;
import uk.ac.sheffield.nlp.fever.annotation.db.FEVERSessionFactory;
import uk.ac.sheffield.nlp.fever.annotation.messaging.messagetypes.AnnotationRequest;
import uk.ac.sheffield.nlp.fever.annotation.messaging.rabbitmq.RabbitMQMessageSender;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.GsonSerializer;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.Serializer;
import uk.ac.sheffield.nlp.fever.annotation.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class Load {

    class LoadClaimEvidence extends ArrayList<Object> {

    }
    class LoadClaim {
        private long id;
        private String claim_text;
        private List<LoadClaimEvidence> evidence;
        private String label;
    }

    public static void main(String[] args) throws IOException, TimeoutException {

        SessionFactory sf = FEVERSessionFactory.getInstance();


        Serializer<LoadClaim> ser = new GsonSerializer<>(LoadClaim.class);
        Session session = FEVERSessionFactory.getInstance().openSession();


        Serializer<AnnotationRequest> qSer = new GsonSerializer<>(AnnotationRequest.class);

        RabbitMQMessageSender<AnnotationRequest> sender1 = new RabbitMQMessageSender<>(qSer, "annotation_task_1");
        RabbitMQMessageSender<AnnotationRequest> sender2 = new RabbitMQMessageSender<>(qSer, "annotation_task_2");
        RabbitMQMessageSender<AnnotationRequest> sender3 = new RabbitMQMessageSender<>(qSer, "annotation_task_3");

        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                Transaction tx = session.beginTransaction();

                LoadClaim loadedClaim = ser.deserialize(line.getBytes());


                Claim claim = new Claim();
                claim.setOriginalId(loadedClaim.id);
                claim.setClaimText(loadedClaim.claim_text);

                session.save(claim);

                Task task = new Task();
                task.setClaim(claim);

                if (loadedClaim.label.equals("SUPPORTS")) {
                    task.setOriginalLabel(Label.SUPPORTED);
                } else if (loadedClaim.label.equals("REFUTES")) {
                    task.setOriginalLabel(Label.REFUTED);
                } else {
                    task.setOriginalLabel(Label.NOT_ENOUGH_INFO);
                }




                session.save(task);

                for (List<Object> e : loadedClaim.evidence) {
                    CandidateEvidence addEv = new CandidateEvidence();
                    addEv.setPage((String)e.get(0));
                    addEv.setLine(((Double)e.get(1)).longValue());
                    addEv.setTask(task);

                    session.save(addEv);
                }





                tx.commit();


                AnnotationRequest req = new AnnotationRequest();
                req.setTaskId(task.getId());

                sender1.send(req);
                sender2.send(req);
                //sender3.send(req);


                if(i++%100 == 0) {
                    System.out.println(i);
                }





            }
        }

        session.close();



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
