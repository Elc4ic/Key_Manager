package com.example.mqtt_app.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LockViewModel : ViewModel() {

    var lockController001 by mutableStateOf(0)

    fun open(){
        lockController001 = 1
    }

    fun close(){
        lockController001 = 0
    }

}