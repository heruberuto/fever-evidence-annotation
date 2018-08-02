package uk.ac.sheffield.nlp.fever.annotation.model;

import java.util.Set;

public class Task {
    private long id;
    private Set<CandidateEvidence> evidence;
    private Claim claim;
    private Label originalLabel;


    public Task() {

    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }


    public Set<CandidateEvidence> getEvidence() {
        return evidence;
    }

    public void setEvidence(Set<CandidateEvidence> evidence) {
        this.evidence = evidence;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Label getOriginalLabel() {
        return originalLabel;
    }

    public void setOriginalLabel(Label originalLabel) {
        this.originalLabel = originalLabel;
    }
}
