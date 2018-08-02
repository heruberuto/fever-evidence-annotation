package uk.ac.sheffield.nlp.fever.annotation;

import java.util.HashMap;
public class Config {


    private static Config instance;

    public synchronized static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private HashMap<String,String> conf;

    private Config() {
        this.conf = new HashMap<>();
    }

    public String get(String key) {
        if(this.conf.containsKey(key)) {
            return this.conf.get(key);
        } else {
            String prop = System.getProperty(key).trim();
            this.conf.put(key,prop);
            return prop;
        }

    }


}


