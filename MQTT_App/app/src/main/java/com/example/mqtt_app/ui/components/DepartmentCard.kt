package com.example.mqtt_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mqtt_app.domain.entity.Department
import drawable.rememberKey
import drawable.rememberKeyOff

@Composable
fun DepartmentCard(department: Department, onItemClicked: (department: Department) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onItemClicked(department) }),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface)
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
            ){
                Text(
                    text = department.name,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = Color.White,
                    fontSize = 28.sp,
                )
            }
            Icon(
                if (false) rememberKeyOff() else rememberKey(),
                "open",
                tint = Color.White
            )
        }

    }
}

@Preview
@Composable
fun PreviewScreen() {
    DepartmentCard(Department("Склад", 1)) {}
}