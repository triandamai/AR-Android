/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.hilwa.ar.ApplicationState
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.BaseScreen
import app.hilwa.ar.base.UIWrapper

object Quiz {
    const val routeName = "Quiz"
}

fun NavGraphBuilder.routeQuiz(
    state: ApplicationState,
) {
    composable(Quiz.routeName) {
        ScreenQuiz(appState = state)
    }
}

@Composable
internal fun ScreenQuiz(
    appState: ApplicationState,
) = UIWrapper<QuizViewModel>(appState = appState) {
    val state by uiState.collectAsState()
    val dataState by uiDataState.collectAsState()

    BaseScreen {

    }

}

@Preview
@Composable
fun PreviewScreenQuiz() {
    BaseMainApp {
        ScreenQuiz(it)
    }
}