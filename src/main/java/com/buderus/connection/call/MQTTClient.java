package com.buderus.connection.call;

import com.buderus.connection.call.subscribe.*;
import com.buderus.connection.config.KM200Converter;
import com.buderus.connection.config.KM200Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MQTTClient {

    private final Logger logger = LoggerFactory.getLogger(MQTTClient.class);

    @Autowired
    private KM200Converter km200Converter;

    @Value("${mosquitto.url}")
    String mosquittoUrl;

    @Value("${mosquitto.port}")
    String mosquittoPort;

    private MqttClient client;
    List<KM200SubscribeValues> topics;

    private Boolean retained;

    //@PostConstruct
    private void initializeMQTTClient() {
        try {
            client = new MqttClient(
                    "tcp://" + mosquittoUrl + ":" + mosquittoPort, //URI
                    MqttClient.generateClientId(), //ClientId
                    new MemoryPersistence()); //Persistence

            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println(cause.getMessage());
                    logger.error("{}", cause.getMessage(), cause);
                    //Called when the client lost the connection to the broker
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    km200Converter.checkPayload(message, getValue(topic));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    try {
                        // add something clever here
                        logger.info("{}", token.getMessage().toString());
                    } catch (MqttException e) {
                        logger.error("{}", e.getMessage(), e);
                    }

                }
            });
            client.connect();
        } catch (MqttException e) {
            logger.error("{}", e.getMessage(), e);
        }
        subscribeAllTopics();
    }

    private void subscribeAllTopics() {
        this.topics = new ArrayList<>();
        topics.addAll(Arrays.asList(SystemValues.values()));
        topics.addAll(Arrays.asList(HeatCircuit1.values()));
        topics.addAll(Arrays.asList(HeatCircuit2.values()));
        topics.addAll(Arrays.asList(HeatSources.values()));
        topics.addAll(Arrays.asList(HolidayMode.values()));
        for (KM200SubscribeValues topic : topics) {
            try {
                client.subscribe(topic.getDescription());
            } catch (MqttException e) {
                logger.error("{}", e.getMessage(), e);
            }
        }
    }

    private KM200SubscribeValues getValue(String topic) {
        KM200SubscribeValues value = null;
        if (!topics.isEmpty()) {
            value = topics.stream().filter(p -> p.getDescription().equals(topic)).findFirst().get();
        }
        return value;
    }

    public void publishTopic(KM200SubscribeValues values, KM200Status km200Status){
        try {

            client.publish(values.getDescription(), km200Converter.convertToPayload(km200Status));
        } catch (MqttException | JsonProcessingException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

    public Boolean getRetained() {
        return retained;
    }

    public void setRetained(Boolean retained) {
        this.retained = retained;
    }
}
