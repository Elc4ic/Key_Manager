package com.example.mqtt_app.domain.entity

import com.example.mqtt_app.R

data class Department(
    val name: String,
    val picture: Int,
)
fun departmentItems(): List<Department> {

    val departments = arrayListOf<Department>()
    departments.add(Department("Интернет вещей", R.drawable.ic_launcher_foreground))
    departments.add(Department("Аэро-космос", R.drawable.ic_launcher_foreground))
    departments.add(Department("Робототехника", R.drawable.ic_launcher_foreground))
    departments.add(Department("Подводка", R.drawable.ic_launcher_foreground))
    departments.add(Department("Мастерская", R.drawable.ic_launcher_foreground))
    departments.add(Department("Склад", R.drawable.ic_launcher_foreground))

    return departments
}



