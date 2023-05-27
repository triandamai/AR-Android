package app.trian.tudu.feature.splash

import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.domain.user.CheckSessionUserUseCase
import app.trian.tudu.feature.auth.onboard.Onboard
import app.trian.tudu.feature.home.Home
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