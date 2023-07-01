/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.augmentedReality.listAR

import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListARViewModel @Inject constructor():MviViewModel<ListARState,ListARIntent,ListARAction>(
    ListARState()
) {
    override fun onAction(action: ListARAction) {
        //
    }
}