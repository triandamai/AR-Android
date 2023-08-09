package app.hilwa.ar.feature.splash

import app.hilwa.ar.data.domain.user.CheckSessionUserUseCase
import app.hilwa.ar.feature.auth.Authentication
import app.hilwa.ar.feature.home.Home
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkSessionUserUseCase: CheckSessionUserUseCase,
) : MviViewModel<SplashState, SplashAction>(
    SplashState()
) {
    init {
        checkIfUserLoggedIn()
    }

    private fun checkIfUserLoggedIn() = async {
        checkSessionUserUseCase().collect {
            delay(500)
            if (it) {
                navigateAndReplace(Home.routeName)
                onCleared()
            } else {
                navigateAndReplace(Authentication.routeName)
                onCleared()
            }
        }

    }

    override fun onAction(action: SplashAction) {
        //no empty
    }

}