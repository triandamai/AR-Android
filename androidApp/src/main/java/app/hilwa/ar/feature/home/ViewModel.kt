/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home


import android.content.Context
import app.trian.mvi.ui.viewModel.BaseViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context:Context,
) : BaseViewModelData<HomeState, HomeDataState, HomeEvent>(context,HomeState(), HomeDataState()) {
    init {
        handleActions()
    }

    override fun handleActions() = onEvent {}

}
