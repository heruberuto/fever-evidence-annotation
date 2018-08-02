package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import uk.ac.sheffield.nlp.fever.annotation.db.FEVERSessionFactory;
import uk.ac.sheffield.nlp.fever.annotation.model.Task;


@Repository
public class TaskDAOImpl extends AbstractDAO implements TaskDAO {

    public TaskDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Task get(long taskId) {
        Session session = sessionFactory.openSession();
        Task t = session.get(Task.class, taskId);
        Hibernate.initialize(t.getEvidence());
        Hibernate.initialize(t.getClaim());
        session.close();
        return t;
    }

}
