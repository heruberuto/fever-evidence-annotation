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


        //Set username and password from D-args
        factory.setUsername(Config.getInstance().get("messaging.username"));
        factory.setPassword(Config.getInstance().get("messaging.password"));
        factory.setHost(Config.getInstance().get("messaging.host"));



        final Connection connection;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queue, true, false, false, null);
            channel.basicQos(1);




        } catch (IOException | TimeoutException e) {
            e.printStackTrace();


            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            run();
        }



    }


    public Channel getChannel() {
        return channel;
    }
}

