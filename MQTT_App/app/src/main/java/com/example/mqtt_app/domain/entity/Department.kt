package com.example.mqtt_app.domain.entity

import com.example.mqtt_app.R

data class Department(
    val name: String,
    val picture: Int,
)
fun departmentItems(): List<Department> {

    val departments = arrayListOf<Department>()
    departments.add(Department("Пустышка", R.drawable.ic_launcher_foreground))
    departments.add(Department("Мастерская", R.drawable.ic_launcher_foreground))
    departments.add(Department("Склад", R.drawable.ic_launcher_foreground))
    departments.add(Department("Интернет вещей", R.drawable.ic_launcher_foreground))

    return departments
}



