package uk.ac.sheffield.nlp.fever.annotation.dao;

import uk.ac.sheffield.nlp.fever.annotation.model.Annotation;
import uk.ac.sheffield.nlp.fever.annotation.model.CandidateEvidence;
import uk.ac.sheffield.nlp.fever.annotation.model.Task;

import java.util.List;
import java.util.Set;

public interface EvidenceDAO extends GenericDAO {

    Set<CandidateEvidence> getEvidence(Task task, List<Long> ids);
}
