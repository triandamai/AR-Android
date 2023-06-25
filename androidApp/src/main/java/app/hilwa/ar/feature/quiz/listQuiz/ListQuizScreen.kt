/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.listQuiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.base.format
import app.hilwa.ar.components.DialogLoading
import app.hilwa.ar.components.ItemQuiz
import app.hilwa.ar.feature.quiz.Quiz
import app.hilwa.ar.feature.quiz.detailQuiz.DetailQuiz
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseMainApp
import app.trian.mvi.ui.BaseScreen
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController
import com.google.firebase.Timestamp

object ListQuiz {
    const val routeName = "ListQuiz"
}


@Navigation(
    route = ListQuiz.routeName,
    group = Quiz.routeName,
    viewModel = ListQuizViewModel::class
)
@Composable
internal fun ScreenListQuiz(
    uiContract: UIContract<ListQuizState, ListQuizIntent, ListQuizAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract) {
    val modalBottomSheet =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    BaseScreen(
        modalBottomSheetState = modalBottomSheet,
        topAppBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Daftar Quiz"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = 0.dp
            )
        }
    ) {
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                content = {
                    items(state.quiz) {
                        ItemQuiz(
                            quizName = it.quizTitle,
                            quizImage = it.quizImage,
                            quizProgress = it.progress,
                            quizAmountQuestion = it.question.size,
                            createdAt = it.createdAt.format(),
                            onClick = {
                                navigator.navigateSingleTop(DetailQuiz.routeName, it.id)
                            }
                        )
                    }
                })
        }

    }

}

@Preview
@Composable
fun PreviewScreenListQuiz() {
    BaseMainApp {
        ScreenListQuiz(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = ListQuizState(),
            )
        )
    }
}