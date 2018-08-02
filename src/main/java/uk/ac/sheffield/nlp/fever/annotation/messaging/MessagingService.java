package uk.ac.sheffield.nlp.fever.annotation.messaging;

import java.util.List;

public interface MessagingService<T> {

    void addHandler(MessageHandler<T> handler);
    List<MessageHandler<T>> getHandlers();

}
