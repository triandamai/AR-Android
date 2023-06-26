/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.arView

import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArViewViewModel @Inject constructor():MviViewModel<ArViewState,ArViewIntent,ArViewAction>(
    ArViewState()
) {
    override fun onAction(action: ArViewAction) {
        //no emptty
    }
}