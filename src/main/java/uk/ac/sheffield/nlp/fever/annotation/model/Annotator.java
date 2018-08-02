package uk.ac.sheffield.nlp.fever.annotation.model;

import java.time.Instant;
import java.util.Set;

public class Annotator {
    private long id;
    private String auth;
    private Instant createdDate;
    private Set<Annotation> annotations;

    public Annotator() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }


}
