package app.hilwa.ar.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.hilwa.ar.ApplicationState
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.UIWrapper

object Splash {
    const val routeName = "Splash"
}

fun NavGraphBuilder.routeSplash(
    state: ApplicationState,
) {
    composable(Splash.routeName) {
        ScreenSplash(state = state)
    }
}

@Composable
internal fun ScreenSplash(
    state: ApplicationState,
) = UIWrapper<SplashViewModel>(appState = state) {

    LaunchedEffect(key1 = this, block = {
       dispatch(SplashEvent.CheckSession)
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {}
}

@Preview
@Composable
fun PreviewScreenSplash() {
    BaseMainApp {
        ScreenSplash(it)
    }
}