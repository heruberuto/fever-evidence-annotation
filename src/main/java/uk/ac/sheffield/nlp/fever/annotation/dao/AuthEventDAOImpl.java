package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AuthEventDAOImpl extends AbstractDAO implements AuthEventDAO{
    protected AuthEventDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
