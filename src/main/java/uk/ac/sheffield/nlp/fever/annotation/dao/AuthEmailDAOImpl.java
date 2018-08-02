package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import uk.ac.sheffield.nlp.fever.annotation.model.AuthEmail;
import uk.ac.sheffield.nlp.fever.annotation.model.Page;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository
public class AuthEmailDAOImpl extends AbstractDAO implements AuthEmailDAO {
    protected AuthEmailDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public static boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    @Override
    public AuthEmail getEmail(String email) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AuthEmail> query = builder.createQuery(AuthEmail.class);


        Root<AuthEmail> root = query.from(AuthEmail.class);

        AuthEmail result = session.createQuery(query.where(
                builder.equal(root.get("email"),email)
        ).select(root)).getSingleResult();

        session.close();

        return result;
    }
}
