package uk.ac.sheffield.nlp.fever.annotation.messaging.messagetypes;

public class AnnotationRequest {

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    private long taskId;

}
