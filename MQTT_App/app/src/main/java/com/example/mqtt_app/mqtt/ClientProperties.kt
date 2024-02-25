package com.example.mqtt_app.mqtt

//broker info
    const val CLIENT_USER_NAME = "u_18FD72"
    const val CLIENT_PASSWORD = "hVrYAptZ"
    const val MQTT_HOST = "tcp://m9.wqtt.ru:15410"
    const val CONNECTION_TIMEOUT = 3
    const val CONNECTION_KEEP_ALIVE_INTERVAL = 60
    const val CONNECTION_CLEAN_SESSION = true
    const val CONNECTION_RECONNECT = true
//topics
    const val CONTROL_TOPIC = "control/lock001"
    const val STATUS_TOPIC = "status/lock001"
