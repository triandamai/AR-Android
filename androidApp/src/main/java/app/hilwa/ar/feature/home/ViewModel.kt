/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home


import app.trian.mvi.ui.viewModel.MviViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : MviViewModelData<HomeState, HomeDataState, HomeEvent>(HomeState(), HomeDataState()) {
    init {
        handleActions()
    }

    override fun handleActions() = onEvent {}

}
