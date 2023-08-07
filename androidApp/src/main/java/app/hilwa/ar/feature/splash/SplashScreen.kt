package app.hilwa.ar.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.hilwa.ar.components.BaseMainApp
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.contract.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
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
    uiContract: UIContract<SplashState, SplashAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
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