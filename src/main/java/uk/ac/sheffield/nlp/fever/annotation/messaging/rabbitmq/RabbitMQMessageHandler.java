package uk.ac.sheffield.nlp.fever.annotation.messaging.rabbitmq;

import com.rabbitmq.client.*;
import uk.ac.sheffield.nlp.fever.annotation.Config;
import uk.ac.sheffield.nlp.fever.annotation.messaging.MessageHandler;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.Serializer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class RabbitMQMessageHandler<T> implements MessageHandler<T> {

    private Serializer<T> ser;




    private final String queue;


    public RabbitMQMessageHandler(Serializer<T> ser, String queueName) throws IOException, TimeoutException {
        this.queue = queueName;
        this.ser = ser;

        AmqSetup<T> setup = new AmqSetup<>(ser, this.queue);
        Channel channel = setup.getChannel();


        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    onReceive(ser.deserialize(body));
                } catch (Exception e) {
                    System.out.println("Unhandled error in handler!");
                    e.printStackTrace();
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };


        channel.basicConsume(queue, false, consumer);


        Thread t = new Thread(setup);
        t.start();


    }



}

