package uk.ac.sheffield.nlp.fever.annotation.messaging.serializer;

public interface Serializer<T> {

    T deserialize(byte[] byteStream);
    byte[] serialize(T obj);

}
