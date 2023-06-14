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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.base.listener.AREventListener
import app.hilwa.ar.components.ItemQuiz
import app.trian.core.ui.BaseMainApp
import app.trian.core.ui.BaseScreen
import app.trian.core.ui.UIListenerData
import app.trian.core.ui.UiWrapperData
import app.trian.core.ui.extensions.gridItems
import app.trian.core.ui.rememberUIController

object ListQuiz {
    const val routeName = "ListQuiz"
}


@Composable
internal fun ScreenListQuiz(
    uiEvent: UIListenerData<ListQuizState, ListQuizDataState, ListQuizEvent, AREventListener>
) = UiWrapperData(uiEvent) {

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
                           // navigateSingleTop(DetailQuiz.routeName)
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
            uiEvent = UIListenerData(
                controller = rememberUIController(
                    event = AREventListener()
                ),
                state = ListQuizState(),
                data = ListQuizDataState()
            )
        )
    }
}