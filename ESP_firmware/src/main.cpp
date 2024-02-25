#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <Wire.h>


const char *ssid = "WLAN";
const char *pass = "";

const char *mqtt_server = "m9.wqtt.ru";
const int mqtt_port = 15410;
const char *mqtt_user = "u_18FD72";
const char *mqtt_pass = "hVrYAptZ";
const char *noKey = "1";
const char *haveKey = "0";

void callback(char *topic, const byte *payload, int length) {
    char message[5] = {0x00};

    for (int i = 0; i < length; i++)
        message[i] = (char) payload[i];

    Serial.print(topic);
    Serial.print(" => ");
    Serial.println(message);

    if (String(topic) == "control/lock001") {
        message[length] = 0x00;
        String str_msg = String(message);
        int isLock = str_msg.toInt();
        digitalWrite(13, isLock);
    }
}

WiFiClient wclient;
PubSubClient client(mqtt_server, mqtt_port, wclient);

void setup() {
    Serial.begin(115200);
    pinMode(12, INPUT_PULLUP);
    pinMode(13, OUTPUT);

    if (WiFi.status() != WL_CONNECTED) {
        Serial.print("Connecting to ");
        Serial.print(ssid);
        Serial.println("...");
        WiFi.begin(ssid, pass);

        if (WiFi.waitForConnectResult() != WL_CONNECTED)
            return;
        Serial.println("WiFi connected");
    }

    client.setCallback(callback);

    if (!client.connected()) {
        Serial.println("Connecting to MQTT server");
        if (client.connect("ESPLockController", mqtt_user, mqtt_pass)) {
            Serial.println("Connected to MQTT server");
        } else {
            Serial.println("Could not connect to MQTT server");
        }
    }
    client.subscribe("control/lock001");
}

void loop() {
    int LockStatus = digitalRead(12);

    if (client.connected()) {
        client.loop();
        if (LockStatus == 0)client.publish("status/lock001", haveKey);
        else client.publish("status/lock001", noKey);
    }
    delay(300);
}
