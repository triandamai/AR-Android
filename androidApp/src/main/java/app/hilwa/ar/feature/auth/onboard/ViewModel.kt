package app.hilwa.ar.feature.auth.onboard

import android.content.Context
import app.trian.mvi.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    @ApplicationContext context: Context,
) : BaseViewModel<OnboardState, OnboardEvent>(context,OnboardState()) {
    init {
        handleActions()
    }

    override fun handleActions() = onEvent {}

}