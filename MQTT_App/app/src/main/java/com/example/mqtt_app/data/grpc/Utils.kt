package com.example.mqtt_app.data.grpc

import service.Mqtt

object Utils {
    fun Int.buildId(): Mqtt.Id = Mqtt.Id.newBuilder()
        .setId(this)
        .build()

    fun buildEmpty(): Mqtt.Empty = Mqtt.Empty.newBuilder().build()

    fun buildCreateRequest(user: Mqtt.User, password: String): Mqtt.CreateRequest =
        Mqtt.CreateRequest.newBuilder()
            .setUser(user)
            .setPassword(password)
            .build()

    fun buildLoginRequest(email: String, password: String): Mqtt.LoginRequest =
        Mqtt.LoginRequest.newBuilder()
            .setEmail(email)
            .setPassword(password)
            .build()

    fun buildUser(id: Long, email: String, name: String): Mqtt.User = Mqtt.User.newBuilder()
        .setId(id)
        .setEmail(email)
        .setName(name)
        .build()
}