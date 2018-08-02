package uk.ac.sheffield.nlp.fever.annotation.model;

import java.time.Instant;
import java.util.Set;

public class AuthToken {

    private long id;
    private AuthEmail email;
    private Instant createdDate;
    private Instant expiresDate;
    private String token;
    private Set<AuthEvent> events;

    public Set<AuthEvent> getEvents() {
        return events;
    }

    public void setEvents(Set<AuthEvent> events) {
        this.events = events;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Instant expiresDate) {
        this.expiresDate = expiresDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AuthEmail getEmail() {
        return email;
    }

    public void setEmail(AuthEmail email) {
        this.email = email;
    }
}
