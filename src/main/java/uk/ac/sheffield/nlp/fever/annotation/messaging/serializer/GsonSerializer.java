package uk.ac.sheffield.nlp.fever.annotation.messaging.serializer;

import com.google.gson.Gson;

public class GsonSerializer<T> implements Serializer<T> {

    private final Gson gson;
    private final Class<T> type;



    public Class<T> getMyType() {
        return this.type;
    }

    public GsonSerializer(Class<T> type) {

        this.type = type;
        gson = new Gson();
    }

    @Override
    public T deserialize(byte[] byteStream) {
        return gson.fromJson(new String(byteStream),type);
    }

    @Override
    public byte[] serialize(T obj) {
        return gson.toJson(obj).getBytes();
    }
}
