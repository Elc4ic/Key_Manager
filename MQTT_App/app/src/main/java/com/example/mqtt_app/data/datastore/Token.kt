package com.example.mqtt_app.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object Token {
    val USER_TOKEN = stringPreferencesKey("user_token")
}