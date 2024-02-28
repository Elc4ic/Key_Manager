package org.example.mqtt_server.Config;

import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.mqtt_server.Entity.Lock;
import org.example.mqtt_server.Repository.LockRepository;

@AllArgsConstructor
public class MqttCustomCallback implements MqttCallback {

    private LockRepository lockRepository;

    public void connectionLost(Throwable cause) {
        System.out.println("The connection is disconnected and can be reconnected");
    }

    public void messageArrived(String topic, MqttMessage message) {
        System.out.println(topic + ":" + message);
        if (topic.contains("status/")) {
            Integer id = Integer.parseInt(topic.substring(7));
            Integer state = Integer.parseInt(message.toString());
            lockRepository.save(Lock.builder().id(id).status(state).build());
        }
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}

