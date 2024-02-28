package org.example.mqtt_server.Config;

import org.eclipse.paho.client.mqttv3.*;
import org.example.mqtt_server.Repository.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfiguration {

    @Autowired
    private LockRepository lockRepository;

    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }

    @Bean
    public IMqttClient mqttClient(@Value("${mqtt.clientId}") String clientId,
                                  @Value("${mqtt.hostname}") String hostname, @Value("${mqtt.port}") int port) throws MqttException {

        IMqttClient mqttClient = new MqttClient("tcp://" + hostname + ":" + port, clientId);

        mqttClient.setCallback(new MqttCustomCallback(lockRepository));

        mqttClient.connect(mqttConnectOptions());

        mqttClient.subscribe("status/1");

        return mqttClient;
    }
}
