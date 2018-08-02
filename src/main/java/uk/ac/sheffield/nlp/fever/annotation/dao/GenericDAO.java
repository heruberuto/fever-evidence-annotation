package uk.ac.sheffield.nlp.fever.annotation.dao;

import uk.ac.sheffield.nlp.fever.annotation.model.Annotation;

public interface GenericDAO {

    void save(Object obj);
    void update(Object annotation);

}
