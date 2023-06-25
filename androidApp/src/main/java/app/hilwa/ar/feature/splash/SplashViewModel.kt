package app.hilwa.ar.feature.splash

import app.hilwa.ar.data.domain.user.CheckSessionUserUseCase
import app.hilwa.ar.feature.auth.Authentication
import app.hilwa.ar.feature.home.Home
import app.trian.mvi.ui.UIEvent
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkSessionUserUseCase: CheckSessionUserUseCase,
) : MviViewModel<SplashState, SplashIntent, SplashAction>(
    SplashState()
) {
    init {
        checkIfUserLoggedIn()
    }
    private fun checkIfUserLoggedIn() = async {
        checkSessionUserUseCase().collect {
            if (it) {
                sendUiEvent(UIEvent.NavigateAndReplace(Home.routeName))
                onCleared()
            } else {
                sendUiEvent(UIEvent.NavigateAndReplace(Authentication.routeName))
                onCleared()
            }
        }

    }

    override fun onAction(action: SplashAction) {
       //no empty
    }

}