package org.example.mqtt_server.config;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.mqtt_server.entity.Lock;
import org.example.mqtt_server.Repository.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MqttCustomCallback implements MqttCallback {

    private LockRepository lockRepository;

    public MqttCustomCallback(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    public void connectionLost(Throwable cause) {
        System.out.println("The connection is disconnected and can be reconnected");
    }

    public void messageArrived(String topic, MqttMessage message) {

        if (topic.equals("status/")) {
            Integer id = message.getId();
            Integer state = Integer.parseInt(message.toString());
            lockRepository.save(Lock.builder().id(id).status(state).build());
            System.out.println("msg: " + message + " " + message.getId());
        }
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}

