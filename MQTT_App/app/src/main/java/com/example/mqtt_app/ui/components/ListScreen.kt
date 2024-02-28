package com.example.mqtt_app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mqtt_app.viewModel.LockViewModel
import drawable.rememberKey
import drawable.rememberKeyOff
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.concurrent.schedule

@Composable
fun ListScreen(
    paddingValues: PaddingValues,
    department: String,
    viewModel: LockViewModel = viewModel(),
) {
    runBlocking {
        viewModel.getLockList()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        viewModel.locks.forEach { i ->
            if (i.status.toString() == department) {
                item(i) {
                    var buttonState by remember { mutableIntStateOf(0) }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(4.dp),
                        onClick = {
                            runBlocking { viewModel.open(i.id) }
                            buttonState = 1
                        },
                        enabled = buttonState == 0 && i.status == 1
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = i.id.toString(),
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    color = Color.White,
                                    fontSize = 28.sp,
                                )
                            }
                            Icon(
                                if (i.status == 1) rememberKey() else rememberKeyOff(),
                                "open",
                                tint = Color.White
                            )
                        }
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
}
