package uk.ac.sheffield.nlp.fever.annotation.model;

import java.time.Instant;

public class AuthEmail {
    private long id;
    private Annotator annotator;
    private Instant createdDate;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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
}
