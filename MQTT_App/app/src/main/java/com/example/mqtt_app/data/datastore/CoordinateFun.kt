package com.example.mqtt_app.data.datastore

import android.util.Log
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

suspend fun DataCoordinator.getSampleToken(): String {
    val context = this.context ?: return defaultToken
    return context.dataStore.data.firstOrNull()?.get(Token.USER_TOKEN)
        ?: defaultToken
}

@OptIn(DelicateCoroutinesApi::class)
fun DataCoordinator.updateSampleToken(value: String) {
    this.sampleToken = value
    GlobalScope.launch(Dispatchers.Default) {
        setSampleToken(value)
    }
}
suspend fun DataCoordinator.setSampleToken(value: String) {
    val context = this.context ?: return
    Log.i(
        "DataStore",
        " setSampleDataStore."
    )
    context.dataStore.edit { preferences ->
        preferences[Token.USER_TOKEN] = value
        Log.i(
            "DataStore",
            "setSampleDataStore : $value."
        )
    }
}

