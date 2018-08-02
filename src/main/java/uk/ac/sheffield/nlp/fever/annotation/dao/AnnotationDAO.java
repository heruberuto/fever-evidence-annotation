package uk.ac.sheffield.nlp.fever.annotation.dao;

import uk.ac.sheffield.nlp.fever.annotation.model.Annotation;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotator;

import java.util.List;

public interface AnnotationDAO extends GenericDAO {

    List<Annotation> list();

    long count();

    long count(Annotator annotator);

}
