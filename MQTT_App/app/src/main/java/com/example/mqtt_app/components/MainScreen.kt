package com.example.mqtt_app.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mqtt_app.model.LockViewModel
import kotlinx.coroutines.runBlocking
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun MainScreen(
    viewModel: LockViewModel = viewModel(),
) {
    runBlocking {
        viewModel.getLockList()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        viewModel.locks.forEach { i ->
            item(i) {
                var buttonState by remember { mutableStateOf(0) }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(4.dp),
                    onClick = {
                        runBlocking { viewModel.open(i.id) }
                        runBlocking { viewModel.getLockList() }
                        buttonState = 1
                    },
                    enabled = buttonState == 0 && i.status == 1
                ) {
                    Text(text = if (i.status == 1) "Получить ключ" else "Ключа нет")
                }
                if (buttonState == 1) {
                    Timer().schedule(1000) {
                        buttonState = 0
                    }
                }
            }
        }
    }
}
