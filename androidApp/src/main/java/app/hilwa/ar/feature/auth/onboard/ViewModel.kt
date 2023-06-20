package app.hilwa.ar.feature.auth.onboard

import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
) : MviViewModel<OnboardState, OnboardEvent>(OnboardState()) {
    init {
        handleActions()
    }

    override fun handleActions() = onEvent {}

}