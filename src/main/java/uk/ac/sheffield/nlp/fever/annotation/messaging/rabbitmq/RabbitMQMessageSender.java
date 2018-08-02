package uk.ac.sheffield.nlp.fever.annotation.messaging.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import uk.ac.sheffield.nlp.fever.annotation.Config;
import uk.ac.sheffield.nlp.fever.annotation.messaging.MessageSender;
import uk.ac.sheffield.nlp.fever.annotation.messaging.serializer.Serializer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQMessageSender<T> implements MessageSender<T> {


    private Serializer<T> ser;

    private Channel channel;
    private final String queue;


    public RabbitMQMessageSender(Serializer<T> ser, String queueName) throws IOException, TimeoutException {
        this.queue = queueName;
        this.ser = ser;


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Config.getInstance().get("amq.host"));
        factory.setUsername(Config.getInstance().get("amq.user"));
        factory.setPassword(Config.getInstance().get("amq.password"));

        final Connection connection;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(queue, true, false, false, null);



        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }



    }


    @Override
    public void send(T obj) throws IOException {

        if(channel != null) {
            channel.basicPublish( "", queue,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    ser.serialize(obj));
        }
    }

}
