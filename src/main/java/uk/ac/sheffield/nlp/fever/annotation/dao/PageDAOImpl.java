package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import uk.ac.sheffield.nlp.fever.annotation.model.AnnotationAssignment;
import uk.ac.sheffield.nlp.fever.annotation.model.Page;
import uk.ac.sheffield.nlp.fever.annotation.model.Task;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;


@Repository
public class PageDAOImpl extends AbstractDAO implements PageDAO {

    public PageDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Page get(long pageId) {
        Session session = sessionFactory.openSession();
        Page p = session.get(Page.class, pageId);
        session.close();
        return p;
    }

    @Override
    public Page get(String name) {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Page> query = builder.createQuery(Page.class);


        Root<Page> root = query.from(Page.class);

        Page result = session.createQuery(query.where(
                builder.equal(root.get("page"),name)
            ).select(root)).getSingleResult();

        session.close();

        return result;
    }

    @Override
    public String getLine(String page, int line) {

        Page p = get(page);

        if(p == null) {
            return null;
        }

        String[] lines = p.getText().split("\n");

        String[] bits = lines[line].split("\t");

        if(bits.length>1){
            return bits[1];
        } else {
            return null;
        }

    }

}
