package uk.ac.sheffield.nlp.fever.annotation.messaging.rabbitmq;



import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.Serializer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQMessageHandlingService<T> extends RabbitMQMessageHandler<T> {
    public RabbitMQMessageHandlingService(Serializer<T> ser, String queueName) throws IOException, TimeoutException {
        super(ser, queueName);
    }

    @Override
    public void onReceive(T object) {

    }
}
