package com.example.mqtt_app.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mqtt_app.data.datastore.DataCoordinator
import com.example.mqtt_app.data.datastore.getSampleToken
import com.example.mqtt_app.data.datastore.updateSampleToken
import com.example.mqtt_app.properties.HOST
import com.example.mqtt_app.viewModel.YandexViewModel
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthToken
import com.yandex.authsdk.internal.strategy.LoginType
import kotlinx.coroutines.runBlocking

@Composable
fun Login(
    navController: NavController,
    viewModel: YandexViewModel = viewModel()
) {
    val tokenText: String;
    runBlocking {
        DataCoordinator.shared.updateSampleToken("y0_AgAAAAAzaawiAAtkMAAAAAD9I26OAABqg3Z9DmpGoLR1F6fscKssRUPzaw 31524560")
        tokenText = DataCoordinator.shared.getSampleToken()
    }

    if (!tokenText.equals("")) {

        val yandexToken = tokenText.split(" ")

        runBlocking {
            viewModel.requestJwt(YandexAuthToken(yandexToken[0], yandexToken[1].toLong()))
        }
        navController.navigate("departs")
    }

    val launcher = rememberLauncherForActivityResult(viewModel.sdk.contract) {
        when (it) {
            is YandexAuthResult.Success -> viewModel.onSuccessAuth(it.token)
            is YandexAuthResult.Failure -> viewModel.onProccessError(it.exception)
            is YandexAuthResult.Cancelled -> viewModel.onCancelled()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ElevatedButton(onClick = {
            val loginOptions = YandexAuthLoginOptions(LoginType.WEBVIEW)
            launcher.launch(loginOptions)
        }) { Text(HOST) }
    }
}