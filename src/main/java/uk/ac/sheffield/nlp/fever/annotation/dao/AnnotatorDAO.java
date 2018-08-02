package uk.ac.sheffield.nlp.fever.annotation.dao;

import uk.ac.sheffield.nlp.fever.annotation.model.Annotator;

public interface AnnotatorDAO extends GenericDAO {

    Annotator get(String auth);
    Annotator get(long id);

    long getAnnotatorCount(Annotator annotator);
}
