package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotation;
import uk.ac.sheffield.nlp.fever.annotation.model.AnnotationAssignment;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;


@Repository
public class AnnotatorDAOImpl extends AbstractDAO implements AnnotatorDAO {
    public AnnotatorDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Annotator get(String auth) {

        return get(1L);

    }

    @Override
    public Annotator get(long annotatorId) {
        Session session = sessionFactory.openSession();
        Annotator a = session.get(Annotator.class, annotatorId);

        session.close();
        return a;
    }

    @Override
    public long getAnnotatorCount(Annotator annotator) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);



        Root<Annotation> root = query.from(Annotation.class);

        long result = session.createQuery(query.where(
            builder.equal(
                    root.get("annotator"),
                    annotator
            )).select(builder.count(root))).getSingleResult();

        session.close();
        return result;
    }

}
