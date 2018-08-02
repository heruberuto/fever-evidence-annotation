package uk.ac.sheffield.nlp.fever.annotation.viewmodel;

public class ResolvedCandidateEvidence {

    private long id;
    private String page;
    private long lineNumber;
    private String evidenceText;


    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getEvidenceText() {
        return evidenceText;
    }

    public void setEvidenceText(String evidenceText) {
        this.evidenceText = evidenceText;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
