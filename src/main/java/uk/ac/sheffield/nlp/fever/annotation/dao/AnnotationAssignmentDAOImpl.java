package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotation;
import uk.ac.sheffield.nlp.fever.annotation.model.AnnotationAssignment;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotator;
import uk.ac.sheffield.nlp.fever.annotation.model.Task;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;

@Repository
public class AnnotationAssignmentDAOImpl extends AbstractDAO implements AnnotationAssignmentDAO  {
    public AnnotationAssignmentDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public boolean check(Task task, Annotator annotator) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);



        Root<AnnotationAssignment> root = query.from(AnnotationAssignment.class);

        boolean result = session.createQuery(query.where(

                builder.and(

                    builder.equal(
                        root.get("task"),
                        task.getId()
                    ),

                    builder.equal(
                        root.get("annotator"),
                        annotator.getId()
                    ),

                    builder.or(
                        builder.isFalse(root.get("skipped")),
                        builder.isNotNull(root.get("annotation")),

                        builder.greaterThan(root.get("expiresDate"), Instant.now())

                    )


                    )).select(builder.count(root))).getSingleResult() > 0;



        session.close();

        return result;

    }



    @Override
    public AnnotationAssignment getActive(Annotator annotator) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AnnotationAssignment> query = builder.createQuery(AnnotationAssignment.class);


        Root<AnnotationAssignment> root = query.from(AnnotationAssignment.class);

        List<AnnotationAssignment> result = session.createQuery(query.where(

                builder.and(

                        builder.equal(
                                root.get("annotator"),
                                annotator.getId()
                        ),

                        builder.isNull(root.get("annotation")),
                        builder.isFalse(root.get("skipped")),
                        builder.greaterThan(root.get("expiresDate"), Instant.now())




                )).select(root)).getResultList();




        AnnotationAssignment ret = null;
        if (result.size()>0) {
            ret = result.get(0);
            Hibernate.initialize(ret.getTask());
        }

        session.close();

        return ret;

    }

    @Override
    public AnnotationAssignment checkActive(Task task, Annotator annotator) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AnnotationAssignment> query = builder.createQuery(AnnotationAssignment.class);


        Root<AnnotationAssignment> root = query.from(AnnotationAssignment.class);

        try {
            AnnotationAssignment result = session.createQuery(query.where(

                    builder.and(

                            builder.equal(
                                    root.get("annotator"),
                                    annotator.getId()
                            ),

                            builder.isNull(root.get("annotation")),
                            builder.isFalse(root.get("skipped")),
                            builder.greaterThan(root.get("expiresDate"), Instant.now()),
                            builder.equal(root.get("task"), task)

                    )).select(root)).getSingleResult();
            session.close();
            return result;
        }catch (NoResultException nex) {
            session.close();
            return null;
        }






    }
}
