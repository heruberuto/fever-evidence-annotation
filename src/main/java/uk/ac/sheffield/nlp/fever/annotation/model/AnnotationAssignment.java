package uk.ac.sheffield.nlp.fever.annotation.model;


import java.time.Instant;

public class AnnotationAssignment {

    private long id;
    private Instant createdDate;
    private Instant expiresDate;
    private boolean skipped;
    private Annotator annotator;
    private Task task;
    private Annotation annotation;

    public AnnotationAssignment() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Instant expiresDate) {
        this.expiresDate = expiresDate;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public Annotator getAnnotator() {
        return annotator;
    }

    public void setAnnotator(Annotator annotator) {
        this.annotator = annotator;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }



}
