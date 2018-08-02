package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import uk.ac.sheffield.nlp.fever.annotation.db.FEVERSessionFactory;

public class AbstractDAO implements GenericDAO {

    protected SessionFactory sessionFactory;

    protected AbstractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void save(Object annotation) {
        Session session = FEVERSessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(annotation);
        tx.commit();
        session.close();
    }


    @Override
    public void update(Object annotation) {
        Session session = FEVERSessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        session.update(annotation);
        tx.commit();
        session.close();
    }



}
