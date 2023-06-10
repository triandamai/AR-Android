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
import app.hilwa.ar.UIController
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.BaseScreen
import app.hilwa.ar.base.UIWrapper
import app.hilwa.ar.base.UIWrapperListenerData
import app.hilwa.ar.base.pageWrapper
import app.hilwa.ar.base.extensions.gridItems
import app.hilwa.ar.components.ItemQuiz
import app.hilwa.ar.feature.quiz.detailQuiz.DetailQuiz
import app.hilwa.ar.rememberUIController

object ListQuiz {
    const val routeName = "ListQuiz"
}


@Composable
internal fun ScreenListQuiz(
    state: ListQuizState = ListQuizState(),
    data: ListQuizDataState = ListQuizDataState(),
    invoker: UIWrapperListenerData<ListQuizState, ListQuizDataState, ListQuizEvent>
) = UIWrapper(invoker = invoker) {

    BaseScreen(
        controller=controller,
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
                gridItems(data.quiz, columnCount = 2) {
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
        ScreenListQuiz(
            invoker = UIWrapperListenerData(
                controller = rememberUIController(),
                state = ListQuizState(),
                data = ListQuizDataState()
            )
        )
    }
}