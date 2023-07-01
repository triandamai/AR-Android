/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.augmentedReality.listAR

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController
import app.trian.mvi.ui.theme.ApplicationTheme

object ListAR {
    const val routeName = "ListAR"
}

@Navigation(
    route = ListAR.routeName,
    viewModel = ListARViewModel::class
)
@Composable
fun ListARScreen(
    uiContract: UIContract<ListARState, ListARIntent, ListARAction>,
    eventListener: BaseEventListener = EventListener()
) = UIWrapper(uiContract = uiContract) {

}

@Preview
@Composable
fun PreviewListARScreen() {
    ApplicationTheme {
        ListARScreen(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = ListARState()
            )
        )
    }
}