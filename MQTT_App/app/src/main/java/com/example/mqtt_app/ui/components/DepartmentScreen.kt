package com.example.mqtt_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartmentScreen(
    navController: NavController,
    department: String
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(department) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
/*                    titleContentColor = colorResource(id = R.color.text)*/
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                }
            )
        },

        content = {padding ->
            ListScreen(padding,department)
        }
    )
}