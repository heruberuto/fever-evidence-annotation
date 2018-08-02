package uk.ac.sheffield.nlp.fever.annotation.model;


import java.time.Instant;
import java.util.Set;

public class Annotation {

    private long id;
    private Instant createdDate;
    private Annotator annotator;
    private Label label;
    private AnnotationAssignment assignment;
    private Task task;
    private Set<CandidateEvidence> partialEvidence;
    private Set<CandidateEvidence> individualEvidence;

    public Set<CandidateEvidence> getIndividualEvidence() {
        return individualEvidence;
    }

    public void setIndividualEvidence(Set<CandidateEvidence> individualEvidence) {
        this.individualEvidence = individualEvidence;
    }

    public Set<CandidateEvidence> getPartialEvidence() {
        return partialEvidence;
    }

    public void setPartialEvidence(Set<CandidateEvidence> partialEvidence) {
        this.partialEvidence = partialEvidence;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public AnnotationAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(AnnotationAssignment assignment) {
        this.assignment = assignment;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Annotation() {

    }

    public Annotator getAnnotator() {
        return annotator;
    }

    public void setAnnotator(Annotator annotator) {
        this.annotator = annotator;
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
}
