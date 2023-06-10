package app.hilwa.ar.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.UiWrapperData
import app.hilwa.ar.base.UIWrapperListenerData
import app.hilwa.ar.rememberUIController

object Splash {
    const val routeName = "Splash"
}

@Composable
internal fun ScreenSplash(
    invoker: UIWrapperListenerData<SplashUiState, SplashUiDataState, SplashEvent>,
) = UiWrapperData(invoker = invoker) {
    LaunchedEffect(key1 = this, block = {
        dispatch(SplashEvent.CheckSession)
    })
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }
}

@Preview
@Composable
fun PreviewScreenSplash() {
    BaseMainApp {
        ScreenSplash(
            invoker = UIWrapperListenerData(
                controller = rememberUIController(),
                state=SplashUiState(),
                data=SplashUiDataState(),
                dispatcher={}
            )
        )
    }
}