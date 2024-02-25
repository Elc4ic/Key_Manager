package com.example.mqtt_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mqtt_app.components.MainScreen
import com.example.mqtt_app.mqtt.CONTROL_TOPIC
import com.example.mqtt_app.mqtt.MQTTClient
import com.example.mqtt_app.mqtt.MQTT_HOST
import com.example.mqtt_app.mqtt.STATUS_TOPIC
import com.example.mqtt_app.ui.theme.MQTT_AppTheme
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage

class MainActivity : ComponentActivity() {

    private var textStatus001 by mutableStateOf(1)

    private val mqttClient by lazy {
        MQTTClient(this)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMqttCallBack()

        setContent {
            MQTT_AppTheme {
                var isSubscribe by remember { mutableStateOf(false) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(4.dp),
                            onClick = {
                                mqttClient.subscribe(CONTROL_TOPIC)
                                mqttClient.subscribe(STATUS_TOPIC)
                                isSubscribe = true
                            }, enabled = !isSubscribe
                        ) {
                            Text(
                                text = "Subscribe",
                            )
                        }
                    },
                    content = { padding ->
                        MainScreen(padding,textStatus001, mqttClient)
                    }
                )
            }
        }
    }

    private fun setMqttCallBack() {
        mqttClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {
                val msg = "Connected to host:\n'$MQTT_HOST'."
                Log.w("Debug", msg)
            }

            override fun connectionLost(throwable: Throwable) {
                val msg = "Connection to host lost:\n'$MQTT_HOST'"
                Log.w("Debug", msg)
            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                if (topic == STATUS_TOPIC) {
                    textStatus001 = mqttMessage.toString().toInt()
                    Log.w("Debug", "'$topic'=> ${mqttMessage.toString().toInt()}")
                }
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {
                Log.w("Debug", "Message published to host '$MQTT_HOST'")
            }
        })
    }

    override fun onDestroy() {
        mqttClient.destroy()
        super.onDestroy()
    }
}
