package uk.ac.sheffield.nlp.fever.annotation.viewmodel;

public class AuthenticationRequest {

    private String auth;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public AuthenticationRequest(String auth) {
        this.auth = auth;
    }

}
