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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.base.format
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.components.ItemQuiz
import app.hilwa.ar.feature.quiz.detailQuiz.DetailQuiz
import app.hilwa.ar.feature.quiz.historyQuiz.HistoryQuiz
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseMainApp
import app.trian.mvi.ui.BaseScreen
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController

object ListQuiz {
    const val routeName = "ListQuiz"
}


@Navigation(
    route = ListQuiz.routeName,
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
                actions = {
                    IconButton(onClick = {
                        navigator.navigateSingleTop(HistoryQuiz.routeName)
                    }) {
                        Icon(imageVector = Icons.Outlined.Leaderboard, contentDescription = "")
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = 0.dp
            )
        }
    ) {
        when {
            (state.isLoading) -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            (state.isEmpty) -> {
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
                        items(state.quiz) {
                            ItemQuiz(
                                quizName = it.quizTitle,
                                quizImage = it.quizImage,
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