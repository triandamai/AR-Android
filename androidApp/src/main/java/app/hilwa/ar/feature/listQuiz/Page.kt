/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.listQuiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.hilwa.ar.ApplicationState
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.UIWrapper

object ListQuiz {
    const val routeName = "ListQuiz"
}

fun NavGraphBuilder.routeListQuiz(
    state: ApplicationState,
) {
    composable(ListQuiz.routeName) {
        ScreenListQuiz(appState = state)
    }
}

@Composable
internal fun ScreenListQuiz(
    appState: ApplicationState,
) = UIWrapper<ListQuizViewModel>(appState = appState) {
    val state by uiState.collectAsState()
    val dataState by uiDataState.collectAsState()

}

@Preview
@Composable
fun PreviewScreenListQuiz() {
    BaseMainApp {
        ScreenListQuiz(it)
    }
}