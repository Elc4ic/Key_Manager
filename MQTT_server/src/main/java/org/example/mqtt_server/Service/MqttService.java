package org.example.mqtt_server.Service;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    @Autowired
    private IMqttClient mqttClient;


    public void publish(final String topic, final String payload, int qos, boolean retained)
            throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(payload.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);

        mqttClient.publish(topic, mqttMessage);
    }

    public void subscribe(final String topic) throws MqttException {
        mqttClient.subscribe(topic);
    }
}
