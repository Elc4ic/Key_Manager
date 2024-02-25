package com.example.mqtt_app.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mqtt_app.model.LockViewModel
import com.example.mqtt_app.mqtt.CONTROL_TOPIC
import com.example.mqtt_app.mqtt.MQTTClient
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun MainScreen(
    padding: PaddingValues,
    stateText: Int,
    mqttClient: MQTTClient,
    viewModel: LockViewModel = viewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(4.dp),
            onClick = {
                viewModel.open()
                mqttClient.publish(CONTROL_TOPIC, viewModel.lockController001.toString())
            },
            enabled = viewModel.lockController001 == 0 && stateText == 1
        ) {
            Text(text = if (stateText == 1) "Получить ключ" else "Ключа нет")
        }
        if (viewModel.lockController001 == 1) {
            Timer().schedule(1000) {
                viewModel.close()
                mqttClient.publish(CONTROL_TOPIC, viewModel.lockController001.toString())
            }
        }
    }
}
