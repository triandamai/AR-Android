package app.hilwa.ar.feature.splash

import app.hilwa.ar.base.BaseViewModel
import app.hilwa.ar.data.domain.user.CheckSessionUserUseCase
import app.hilwa.ar.feature.auth.onboard.Onboard
import app.hilwa.ar.feature.home.Home
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkSessionUserUseCase: CheckSessionUserUseCase,
) : BaseViewModel<SplashUiState, SplashEvent>(SplashUiState()) {
    init {
        handleActions()
    }

    private fun checkIfUserLoggedIn() = async {
        checkSessionUserUseCase().collect{
            if (it) {
                navigateAndReplaceAll(Home.routeName)
                onCleared()
            } else {
                navigateAndReplaceAll(Onboard.routeName)
                onCleared()
            }
        }

    }


    override fun handleActions() = onEvent {
        when (it) {
            SplashEvent.CheckSession -> checkIfUserLoggedIn()
        }
    }

}