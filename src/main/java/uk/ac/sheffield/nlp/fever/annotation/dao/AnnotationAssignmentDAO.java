package uk.ac.sheffield.nlp.fever.annotation.dao;

import uk.ac.sheffield.nlp.fever.annotation.model.AnnotationAssignment;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotator;
import uk.ac.sheffield.nlp.fever.annotation.model.Task;

public interface AnnotationAssignmentDAO extends GenericDAO {
    boolean check(Task task, Annotator annotator);

    AnnotationAssignment getActive(Annotator annotator);

    AnnotationAssignment checkActive(Task task, Annotator annotator);
}
