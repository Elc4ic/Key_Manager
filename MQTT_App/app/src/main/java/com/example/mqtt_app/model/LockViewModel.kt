package com.example.mqtt_app.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mqtt_app.grpc.GrpcClient
import service.Mqtt.Lock

class LockViewModel : ViewModel() {

    private var _locks: List<Lock> = mutableStateListOf()
    private val client = GrpcClient()

    suspend fun getLockList() {
        val locksTemp = arrayListOf<Lock>()
        client.getLockList().collect {
            locksTemp.add(it)
            Log.w("Debug", "${it.id} ${it.control} ${it.status}")
        }
        _locks = locksTemp
        Log.w("Debug", "$_locks")
    }

    suspend fun open(id: Int) {
            client.open(id)
    }

    val locks: List<Lock> get() = _locks

}