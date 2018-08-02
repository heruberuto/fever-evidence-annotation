package uk.ac.sheffield.nlp.fever.annotation.messaging;

import java.io.IOException;

public interface MessageSender<T> {

    void send(T message) throws IOException;
    
}
