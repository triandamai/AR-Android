/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.about

import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor() : MviViewModel<AboutState, AboutAction>(
    AboutState()
) {
    override fun onAction(action: AboutAction) {
        //no empty
    }
}