package uk.ac.sheffield.nlp.fever.annotation.messaging;

public interface MessageHandler<T> {

    void onReceive(T object);

}
