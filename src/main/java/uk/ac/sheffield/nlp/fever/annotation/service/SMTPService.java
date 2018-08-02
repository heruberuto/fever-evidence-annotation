package uk.ac.sheffield.nlp.fever.annotation.service;

import org.apache.commons.mail.EmailException;
import uk.ac.sheffield.nlp.fever.annotation.model.AuthToken;

public interface SMTPService {

    void send(AuthToken token) throws EmailException;

}
