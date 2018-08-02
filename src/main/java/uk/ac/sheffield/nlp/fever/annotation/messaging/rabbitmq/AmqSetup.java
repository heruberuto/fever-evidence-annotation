package uk.ac.sheffield.nlp.fever.annotation.messaging.rabbitmq;


import com.rabbitmq.client.*;
import uk.ac.sheffield.nlp.fever.annotation.Config;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.Serializer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AmqSetup<T> implements Runnable {

    private String queue;
    private Serializer<T> ser;

    private Channel channel;

    public AmqSetup(Serializer<T> ser, String queue) {
        this.queue = queue;
        this.ser = ser;
    }

    @Override
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Config.getInstance().get("amq.host"));
        factory.setUsername(Config.getInstance().get("amq.user"));
        factory.setPassword(Config.getInstance().get("amq.password"));

        final Connection connection;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queue, true, false, false, null);
            channel.basicQos(1);




        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }



    }


    public Channel getChannel() {
        return channel;
    }
}

