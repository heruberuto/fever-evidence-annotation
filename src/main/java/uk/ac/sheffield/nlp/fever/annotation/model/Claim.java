package uk.ac.sheffield.nlp.fever.annotation.model;

public class Claim {
    private long id;
    private long originalId;
    private String claimText;

    public Claim() {

    }

    public String getClaimText() {
        return claimText;
    }

    public void setClaimText(String claimText) {
        this.claimText = claimText;
    }

    public long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(long originalId) {
        this.originalId = originalId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
