package uk.ac.sheffield.nlp.fever.annotation.viewmodel;

import uk.ac.sheffield.nlp.fever.annotation.model.CandidateEvidence;

import java.util.Set;

public class AnnotationTask {

    private long claimId;
    private String claimText;
    private Set<ResolvedCandidateEvidence> evidence;




    public String getClaimText() {
        return claimText;
    }

    public void setClaimText(String claimText) {
        this.claimText = claimText;
    }

    public Set<ResolvedCandidateEvidence> getEvidence() {
        return evidence;
    }

    public void setEvidence(Set<ResolvedCandidateEvidence> evidence) {
        this.evidence = evidence;
    }

    public long getClaimId() {
        return claimId;
    }

    public void setClaimId(long claimId) {
        this.claimId = claimId;
    }
}
