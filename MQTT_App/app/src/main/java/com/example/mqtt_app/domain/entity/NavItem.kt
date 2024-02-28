package com.example.mqtt_app.domain.entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val route: String,
    val ladel: String,
    val icon: ImageVector
) {
    object Departments : NavItem("departs", "Home", Icons.Filled.Home)
    object Login : NavItem("login", "LogIn", Icons.Filled.AccountBox)

}
