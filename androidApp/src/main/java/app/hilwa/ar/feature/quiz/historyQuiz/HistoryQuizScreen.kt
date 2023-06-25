/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.historyQuiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.feature.quiz.historyQuiz.components.ItemHistoryQuiz
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseScreen
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController
import app.trian.mvi.ui.theme.ApplicationTheme

object HistoryQuiz {
    const val routeName = "HistoryQuiz"
}

@Navigation(
    route = HistoryQuiz.routeName,
    viewModel = HistoryViewModel::class
)
@Composable
fun HistoryQuizScreen(
    uiContract: UIContract<HistoryState, HistoryIntent, HistoryAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract = uiContract) {
    LaunchedEffect(key1 = Unit, block = {
        dispatch(HistoryAction.GetData)
    })
    BaseScreen(
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
        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.isEmpty -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sepertnya kamu belum mengambil quiz apapun",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ButtonPrimary(text = "Kerjakan Quiz") {
                        navigator.navigateUp()
                    }
                }
            }

            else -> {
                LazyColumn(
                    content = {
                        items(state.histories) {
                            ItemHistoryQuiz(
                                quizName = it.first.quizTitle,
                                quizAmountRightAnswer = "${it.first.quizDuration} Jawaban Benar",
                                score = it.second.quizScore
                            )
                        }
                    }
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewHistoryQuizScreen() {
    ApplicationTheme {
        HistoryQuizScreen(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = HistoryState()
            )
        )
    }
}