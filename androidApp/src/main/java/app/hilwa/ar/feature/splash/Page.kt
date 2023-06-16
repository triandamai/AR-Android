package app.hilwa.ar.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.trian.core.annotation.Navigation
import app.trian.core.ui.BaseMainApp
import app.trian.core.ui.UIListenerData
import app.trian.core.ui.UIWrapper
import app.trian.core.ui.rememberUIController

object Splash {
    const val routeName = "Splash"
}

@Navigation(
    route = Splash.routeName,
    viewModel = SplashViewModel::class
)
@Composable
internal fun ScreenSplash(
    uiEvent: UIListenerData<SplashUiState, SplashUiDataState, SplashEvent>,
) = UIWrapper(uiEvent) {
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
            uiEvent = UIListenerData(
                controller = rememberUIController(),
                state = SplashUiState(),
                data = SplashUiDataState(),
                dispatcher = {}
            )
        )
    }
}