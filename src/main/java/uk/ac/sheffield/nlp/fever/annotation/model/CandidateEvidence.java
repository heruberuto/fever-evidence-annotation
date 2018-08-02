package uk.ac.sheffield.nlp.fever.annotation.model;

public class CandidateEvidence {
    private long id;
    private long line;
    private String page;
    private Task task;


    public CandidateEvidence() {

    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public long getLine() {
        return line;
    }

    public void setLine(long line) {
        this.line = line;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
