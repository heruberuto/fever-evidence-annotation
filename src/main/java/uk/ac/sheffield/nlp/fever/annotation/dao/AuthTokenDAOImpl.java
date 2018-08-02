package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import uk.ac.sheffield.nlp.fever.annotation.model.AuthToken;


@Repository
public class AuthTokenDAOImpl extends AbstractDAO implements AuthTokenDAO {
    public AuthTokenDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public AuthToken get(long id) {
        Session session = sessionFactory.openSession();

        AuthToken token = session.get(AuthToken.class,id);
        Hibernate.initialize(token.getEvents());
        Hibernate.initialize(token.getEmail());
        Hibernate.initialize(token.getEmail().getAnnotator());

        session.close();
        return token;
    }
}
