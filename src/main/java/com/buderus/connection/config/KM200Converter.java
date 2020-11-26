package com.buderus.connection.config;

import com.buderus.database.BuderusDatabase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Service
public class KM200Converter {

    private final Logger logger = LoggerFactory.getLogger(KM200Converter.class);

    @Autowired
    BuderusDatabase buderusDatabase;

    public void checkPayload(MqttMessage message, KM200Topic topic) throws IOException, ClassNotFoundException {
        if(topic == null || message == null){
            return;
        }
        String value = message.toString();
        if (topic.equals(KM200Topic.CONNECTED)) {
            setConnectionStatus(topic, KM200ConnectType.contains(value));
        } else {
            try {
                final ObjectMapper mapper = new ObjectMapper();
                setKM200Value(topic, mapper.readValue(value, KM200Status.class));
            } catch (JsonProcessingException e) {
                logger.error("{}", e.getMessage(), e);
            }
        }
    }

    private void setKM200Value(KM200Topic topic, KM200Status status){
        buderusDatabase.insertDocument(topic.getDescription(), topic.toString(), status);
    }

    private void setConnectionStatus(KM200Topic topic, KM200ConnectType connectionStatus){
        buderusDatabase.insertDocument(topic.getDescription(), topic.toString(), connectionStatus.name());
    }

    public KM200Status getStatusByTopic(KM200Topic topic){
        Object result = buderusDatabase.find(topic.getDescription(), topic.toString());
        if (result != null) {
            if (result instanceof KM200Status) {
                return (KM200Status) result;
            }
        }
        return new KM200Status();
    }

    public KM200ConnectType getConnectStatus() {
        Object result = buderusDatabase.find(KM200Topic.CONNECTED.getDescription(), KM200Topic.CONNECTED.toString());
        if (result != null) {
            return KM200ConnectType.valueOf(String.valueOf(result));
        }
        return KM200ConnectType.OFF;
    }

    private Object deSerializer(byte[] bytes) throws IOException,
            ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(b);
        return in.readObject();
    }
}
