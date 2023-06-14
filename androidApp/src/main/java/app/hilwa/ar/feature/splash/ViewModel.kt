package app.hilwa.ar.feature.splash

import android.content.Context
import app.hilwa.ar.data.domain.user.CheckSessionUserUseCase
import app.hilwa.ar.feature.auth.onboard.Onboard
import app.hilwa.ar.feature.home.Home
import app.trian.core.ui.viewModel.BaseViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val checkSessionUserUseCase: CheckSessionUserUseCase,
) : BaseViewModelData<SplashUiState, SplashUiDataState, SplashEvent>(context,SplashUiState(),SplashUiDataState()) {
    init {
        handleActions()
    }

    private fun checkIfUserLoggedIn() = async {
        checkSessionUserUseCase().collect {
            if (it) {
                navigation.navigateAndReplace(Home.routeName)
                onCleared()
            } else {
                navigation.navigateAndReplace(Onboard.routeName)
                onCleared()
            }
        }

    }


    override fun handleActions() = onEvent {
        when(it){
            SplashEvent.CheckSession -> checkIfUserLoggedIn()
        }
    }

}