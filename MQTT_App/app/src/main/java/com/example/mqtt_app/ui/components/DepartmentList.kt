package com.example.mqtt_app.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mqtt_app.domain.entity.departmentItems
import com.example.mqtt_app.ui.icons.rememberAccountBox
import com.example.mqtt_app.viewModel.LockViewModel
import drawable.rememberKey
import drawable.rememberKeyOff
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.concurrent.schedule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartmentList(
    navController: NavController,
    viewModel: LockViewModel = viewModel()
) {
    val departs = departmentItems()
    Log.w("Debug", "list open")
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Информация:", fontSize = 28.sp)
                Text("Имя", fontSize = 28.sp)
                Text("Роль:", fontSize = 28.sp)
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "CPD key manager",
                            fontSize = 28.sp,
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.onSurface
                    ),
                    actions = {
                        Row(
                            modifier = Modifier
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = rememberAccountBox(),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }
                            )
                        }
                    },
                )
            },
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    runBlocking {
                        viewModel.getLockList()
                    }
                    viewModel.locks.forEach { i ->
                        item(i) {
                            var buttonState by remember { mutableIntStateOf(0) }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                onClick = {
                                    runBlocking { viewModel.open(i.id) }
                                    buttonState = 1
                                },
                                enabled = buttonState == 0 && i.status == 1,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.weight(1f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = departs[i.id].name,
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                            color = if (i.status == 1) Color.White else Color.Black,
                                            fontSize = 28.sp,
                                        )
                                    }
                                    if (i.status == 1) {
                                        Icon(
                                            rememberKey(),
                                            "open",
                                            tint = Color.White
                                        )
                                    } else {
                                        Icon(
                                            rememberKeyOff(),
                                            "open",
                                            tint = Color.Black
                                        )
                                    }
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
        )
    }
}