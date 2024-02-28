package com.example.mqtt_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mqtt_app.data.datastore.DataCoordinator
import com.example.mqtt_app.domain.entity.NavItem
import com.example.mqtt_app.ui.components.DepartmentList
import com.example.mqtt_app.ui.components.Login
import com.example.mqtt_app.ui.theme.MQTT_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataCoordinator.shared.initialize(this) {

        }

        setContent {
            val navController = rememberNavController()

            MQTT_AppTheme {
                NavHost(navController, startDestination = NavItem.Login.route) {
                    composable(
                        NavItem.Departments.route
                    ) {
                        DepartmentList(navController)
                    }
                    composable(
                        NavItem.Login.route
                    ) {
                        Login(navController)
                    }
                }
            }
        }
    }
}

