package com.example.mqtt_app.grpc

import service.Mqtt

object Utils {
    fun Int.buildId(): Mqtt.Id = Mqtt.Id.newBuilder()
        .setId(this)
        .build()

    fun buildEmpty(): Mqtt.Empty = Mqtt.Empty.newBuilder().build()
}