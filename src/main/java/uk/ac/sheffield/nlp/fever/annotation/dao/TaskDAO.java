package uk.ac.sheffield.nlp.fever.annotation.dao;

import uk.ac.sheffield.nlp.fever.annotation.model.Task;

public interface TaskDAO extends GenericDAO  {

    Task get(long taskId);

}
