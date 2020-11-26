package com.buderus.connection.call;

import com.buderus.connection.config.KM200Converter;
import com.buderus.connection.config.KM200Topic;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @PostConstruct
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
                    System.out.println(topic + ": " + Arrays.toString(message.getPayload()));
                    km200Converter.checkPayload(message, KM200Topic.descriptionOf(topic));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
                }
            });
            client.connect();
        } catch (MqttException e) {
            logger.error("{}", e.getMessage(), e);
        }
        subscribeAllTopics();
    }

    private void subscribeAllTopics() {
        List<KM200Topic> topics = Arrays.asList(KM200Topic.values());
        for (KM200Topic topic : topics) {
            try {
                client.subscribe(topic.getDescription());
            } catch (MqttException e) {
                logger.error("{}", e.getMessage(), e);
            }
        }
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }
}
