package com.example.mqtt_app.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mqtt_app.data.grpc.GrpcClient
import kotlinx.coroutines.launch
import service.Mqtt.Lock

class LockViewModel : ViewModel() {

    var openConnect = mutableStateOf(false)
    var locks = mutableStateListOf<Lock>()
    private val client = GrpcClient()


    suspend fun getLockList() {
        Log.w("Debug", "stream open")
        if (!openConnect.value){
            openConnect.value = true
            viewModelScope.launch{
                client.getLockList().collect {
                    if (it.id == 0) locks.clear()
                    else locks.add(it)
                    Log.w("Debug", "${it.id} ${it.status}")
                }
            }
        }
    }

    suspend fun open(id: Int) {
        client.open(id)
    }
}