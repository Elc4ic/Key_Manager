package com.example.mqtt_app.data.grpc

import com.example.mqtt_app.data.grpc.Utils.buildEmpty
import com.example.mqtt_app.data.grpc.Utils.buildId
import com.example.mqtt_app.properties.HOST
import com.example.mqtt_app.properties.PORT
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.Flow
import service.AuthServiceGrpcKt
import service.LockServiceGrpcKt
import service.Mqtt
import service.UnauthorizedUserServiceGrpcKt
import service.UserServiceGrpcKt

class GrpcClient {
    private val channel =
        ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build()

    private val lockStub = LockServiceGrpcKt.LockServiceCoroutineStub(channel)
    private val authStub = AuthServiceGrpcKt.AuthServiceCoroutineStub(channel)
    private val createStub =
        UnauthorizedUserServiceGrpcKt.UnauthorizedUserServiceCoroutineStub(channel)
    private val userStub = UserServiceGrpcKt.UserServiceCoroutineStub(channel)

    fun getLockList(): Flow<Mqtt.Lock> {
        return lockStub.getLockList(buildEmpty())
    }

    suspend fun open(id: Int) {
        lockStub.open(id.buildId())
    }

    suspend fun login(request: Mqtt.LoginRequest):Mqtt.LoginResponse {
        return authStub.login(request)
    }

    suspend fun create(request: Mqtt.CreateRequest):Mqtt.CreateResponse{
        return createStub.create(request)
    }

}