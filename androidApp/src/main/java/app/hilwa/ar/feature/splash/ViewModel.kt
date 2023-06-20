package app.hilwa.ar.feature.splash

import android.content.Context
import app.hilwa.ar.data.domain.user.CheckSessionUserUseCase
import app.hilwa.ar.feature.auth.onboard.Onboard
import app.hilwa.ar.feature.home.Home
import app.trian.mvi.ui.viewModel.MviViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkSessionUserUseCase: CheckSessionUserUseCase,
) : MviViewModelData<SplashUiState, SplashUiDataState, SplashEvent>(
    SplashUiState(),
    SplashUiDataState()
) {
    init {
        handleActions()
    }

    private fun checkIfUserLoggedIn() = async {
        checkSessionUserUseCase().collect {
            if (it) {
                controller.navigator.navigateAndReplace(Home.routeName)
                onCleared()
            } else {
                controller.navigator.navigateAndReplace(Onboard.routeName)
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