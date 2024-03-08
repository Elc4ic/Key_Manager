package com.example.mqtt_app.viewModel

import android.app.Application
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mqtt_app.data.datastore.DataCoordinator
import com.example.mqtt_app.data.datastore.updateSampleToken
import com.yandex.authsdk.YandexAuthException
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.YandexAuthToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class YandexViewModel(
    app: Application,
) : AndroidViewModel(app) {

    val sdk: YandexAuthSdk = YandexAuthSdk.create(YandexAuthOptions(app, true))

    private val _progress = MutableStateFlow(false)
    val progress: StateFlow<Boolean> = _progress

    private val _jwtState = MutableStateFlow<String?>(null)

    fun onSuccessAuth(yandexAuthToken: YandexAuthToken) {
        DataCoordinator.shared.updateSampleToken(AuthTokenToString(yandexAuthToken))
        Log.w("OAuth", "get token: $yandexAuthToken")
    }
    fun onProccessError(exception: YandexAuthException) {
        Log.w("OAuth", "get error: $exception")
    }
    fun onCancelled() {
        Log.w("OAuth", "Cancelled")
    }

    fun getJwt(): String {
        return _jwtState.value.toString()
    }
    suspend fun requestJwt(token: YandexAuthToken) {
        viewModelScope.launch(Dispatchers.IO) {
            _progress.emit(true)
            try {
                val jwt = sdk.getJwt(StringToAuthToken(DataCoordinator.shared.defaultToken)).split(".")
                Log.w("Dubug", jwt[2])
                Log.w("Dubug", String(Base64.decode(jwt[0], Base64.DEFAULT)))
                Log.w("Dubug", String(Base64.decode(jwt[1], Base64.DEFAULT)))
                _jwtState.emit(jwt[1])
            } catch (e: YandexAuthException) {
                _jwtState.emit(e.message)
            }
            _progress.emit(false)
        }
    }

    fun AuthTokenToString(token: YandexAuthToken): String {
        return "${token.value} ${token.expiresIn}"
    }
    fun StringToAuthToken(token: String): YandexAuthToken {
        val yandexToken = token.split(" ")
        return YandexAuthToken(yandexToken[0], yandexToken[1].toLong())
    }


}


