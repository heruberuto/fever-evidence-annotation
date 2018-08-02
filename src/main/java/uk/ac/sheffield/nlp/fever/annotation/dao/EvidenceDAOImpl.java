package uk.ac.sheffield.nlp.fever.annotation.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotation;
import uk.ac.sheffield.nlp.fever.annotation.model.CandidateEvidence;
import uk.ac.sheffield.nlp.fever.annotation.model.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class EvidenceDAOImpl extends AbstractDAO implements EvidenceDAO {
    protected EvidenceDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Set<CandidateEvidence> getEvidence(Task task, List<Long> ids) {
        Set<CandidateEvidence> evidence = new HashSet<>();
        Set<CandidateEvidence> found = task.getEvidence();

        for(CandidateEvidence ev : found) {
            if (ids.contains(ev.getId())) {
                evidence.add(ev);
            }
        }
        return evidence;

    }
}
