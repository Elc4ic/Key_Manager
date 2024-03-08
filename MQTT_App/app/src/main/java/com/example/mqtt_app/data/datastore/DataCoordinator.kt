package com.example.mqtt_app.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataCoordinator {
    companion object {
        val shared = DataCoordinator()
        const val identifier = "[DataCoordinator]"
    }

    var context: Context? = null

    var sampleToken: String = ""
    val defaultToken: String = "y0_AgAAAAAzaawiAAtkMAAAAAD9I26OAABqg3Z9DmpGoLR1F6fscKssRUPzaw 31524560"

    private val USER_PREFERENCES_NAME = "user_token"
    val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    @OptIn(DelicateCoroutinesApi::class)
    fun initialize(context: Context, onLoad: () -> Unit) {
        Log.i(identifier, "initialize")

        this.context = context
        GlobalScope.launch(Dispatchers.Default) {
            sampleToken = getSampleToken()

            Log.i(identifier, "get Token :$sampleToken")
            onLoad()
        }
    }
}