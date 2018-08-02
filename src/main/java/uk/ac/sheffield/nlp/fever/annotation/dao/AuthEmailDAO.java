package uk.ac.sheffield.nlp.fever.annotation.dao;

import uk.ac.sheffield.nlp.fever.annotation.model.AuthEmail;

public interface AuthEmailDAO extends GenericDAO {

    AuthEmail getEmail(String email);

}
