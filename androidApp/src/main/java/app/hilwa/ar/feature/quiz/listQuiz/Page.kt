/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.listQuiz

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.hilwa.ar.ApplicationState
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.BaseScreen
import app.hilwa.ar.base.UIWrapper
import app.hilwa.ar.base.extensions.gridItems
import app.hilwa.ar.components.ItemQuiz
import app.hilwa.ar.feature.quiz.detailQuiz.DetailQuiz

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

    BaseScreen(
        topAppBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Daftar Quiz"
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = ""
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = 0.dp
            )
        }
    ) {
        LazyColumn(
            content = {
                gridItems(dataState.quiz, columnCount = 2) {
                    ItemQuiz(
                        quizName = it.quizTitle,
                        quizImage = it.quizImage,
                        quizProgress = it.progress,
                        quizAmountQuestion = it.quizQuestionAmount,
                        onClick = {
                            navigateSingleTop(DetailQuiz.routeName)
                        }
                    )
                }
            })

    }

}

@Preview
@Composable
fun PreviewScreenListQuiz() {
    BaseMainApp {
        ScreenListQuiz(it)
    }
}