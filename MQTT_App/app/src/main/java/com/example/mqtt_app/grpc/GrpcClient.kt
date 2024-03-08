package com.example.mqtt_app.grpc

import com.example.mqtt_app.grpc.Utils.buildEmpty
import com.example.mqtt_app.grpc.Utils.buildId
import com.example.mqtt_app.properties.HOST
import com.example.mqtt_app.properties.PORT
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.Flow
import service.LockServiceGrpcKt
import service.Mqtt

class GrpcClient {
    private val channel =
        ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build()

    private val lockStub = LockServiceGrpcKt.LockServiceCoroutineStub(channel)

    suspend fun getLockList(): Flow<Mqtt.Lock> {
        return lockStub.getLockList(buildEmpty())
    }

    suspend fun open(id: Int) {
        lockStub.open(id.buildId())
    }

}