package uk.ac.sheffield.nlp.fever.annotation.dao;

import uk.ac.sheffield.nlp.fever.annotation.model.AuthToken;

public interface AuthTokenDAO extends GenericDAO {
    AuthToken get(long id);
}
