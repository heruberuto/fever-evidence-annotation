package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.springframework.stereotype.Repository;
import uk.ac.sheffield.nlp.fever.annotation.db.FEVERSessionFactory;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotation;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class AnnotationDAOImpl extends AbstractDAO implements AnnotationDAO {


    public AnnotationDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public List<Annotation> list() {
        Session session = this.sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Annotation> query = builder.createQuery(Annotation.class);

        Root<Annotation> root = query.from(Annotation.class);
        query.select(root);

        List<Annotation> annotations = session.createQuery(query).getResultList();

        session.close();

        return annotations;


    }

    @Override
    public long count() {
        Session session = this.sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<Annotation> root = query.from(Annotation.class);
        query.select(builder.count(root));

        Long annotationsCount = session.createQuery(query).getSingleResult();

        session.close();

        return annotationsCount;
    }

    @Override
    public long count(Annotator annotator) {
        return 0;
    }
}
