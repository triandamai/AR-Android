package app.hilwa.ar.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseMainApp
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.rememberUIController

object Splash {
    const val routeName = "Splash"
}

@Navigation(
    route = Splash.routeName,
    viewModel = SplashViewModel::class
)
@Composable
internal fun SplashScreen(
    uiContract: UIContract<SplashState, SplashIntent, SplashAction>,
) = UIWrapper(uiContract) {
    LaunchedEffect(key1 = this, block = {
        dispatch(SplashAction.CheckSession)
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
        SplashScreen(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = SplashState(),
                dispatcher = {}
            )
        )
    }
}