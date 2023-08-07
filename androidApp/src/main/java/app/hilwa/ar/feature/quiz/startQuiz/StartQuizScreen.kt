/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.components.BaseBottomSheet
import app.hilwa.ar.components.BaseMainApp
import app.hilwa.ar.components.BaseScreen
import app.hilwa.ar.components.BottomSheetConfirmation
import app.hilwa.ar.feature.quiz.startQuiz.component.BottomBarQuiz
import app.hilwa.ar.feature.quiz.startQuiz.component.BottomSheetType
import app.hilwa.ar.feature.quiz.startQuiz.component.ScreenQuiz
import app.hilwa.ar.feature.quiz.startQuiz.component.ScreenQuizResult
import app.hilwa.ar.feature.quiz.startQuiz.component.ScreenType
import app.trian.mvi.Argument
import app.trian.mvi.NavType
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.contract.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController

object StartQuiz {
    const val routeName = "Quiz"
    const val argKey = "quizId"

    const val Timeout = "timeout"
}

@Navigation(
    route = StartQuiz.routeName,
    viewModel = StartQuizViewModel::class
)
@Argument(
    name = StartQuiz.argKey,
    navType = NavType.String
)
@Composable
internal fun StartQuizScreen(
    uiContract: UIContract<StartQuizState, StartQuizAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract) {

    val modalBottomSheet = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                when (state.bottomSheetType) {
                    BottomSheetType.TIMEOUT_CONFIRMATION -> false
                    BottomSheetType.CLOSE_CONFIRMATION -> true
                }
            } else true
        }
    )

    LaunchedEffect(key1 = event, block = {
        event.addOnScreenEventListener { event, data ->
            when (event) {
                StartQuiz.Timeout -> launch {
                    commit { copy(bottomSheetType = BottomSheetType.TIMEOUT_CONFIRMATION) }
                    modalBottomSheet.show()
                }

                else -> Unit
            }
        }
    })

    fun onBackPressed() {
        when (state.screenType) {
            ScreenType.QUIZ -> if (state.currentIndex == 0) {
                launch { modalBottomSheet.show() }
            } else dispatch(StartQuizAction.Prev)

            ScreenType.RESULT, ScreenType.EMPTY -> navigator.navigateUp()
            ScreenType.LOADING -> Unit
        }

    }
    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(
        key1 = Unit,
        block = {
            if (state.currentIndex == 0) {
                event.sendEventToApp("START_TIMER")
            }
        }
    )

    BaseScreen(
        modalBottomSheetState = modalBottomSheet,
        bottomBar = {
            BottomBarQuiz(
                show = state.visibleButton,
                isLastQuestion = ((state.questions.size - 1) == state.currentIndex),
                hasAnswer = state.hasAnswer,
                onNext = {
                    dispatch(StartQuizAction.Next)
                },
                onPrev = {
                    dispatch(StartQuizAction.Prev)
                }

            )
        },
        bottomSheet = {
            when (state.bottomSheetType) {
                BottomSheetType.TIMEOUT_CONFIRMATION -> {
                    BaseBottomSheet(
                        textConfirmation = "Oke",
                        showButtonConfirmation = true,
                        showCloseButton = false,
                        onDismiss = {},
                        onConfirm = {
                            launch {
                                modalBottomSheet.hide()
                                navigator.navigateUp()
                            }
                        }
                    ) {
                        Text(
                            text = "Waktu mengerjakan quiz sudah habis?",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Kamu tidak bisa melanjutkan quiz ini",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                BottomSheetType.CLOSE_CONFIRMATION -> {
                    BottomSheetConfirmation(
                        title = "Yakin keluar dari quiz  ini?",
                        message = "Data quiz yang telah kamu isi akan hilang loh",
                        textConfirmation = "Keluar",
                        textCancel = "Batal",
                        onDismiss = {
                            launch {
                                modalBottomSheet.hide()
                            }
                        },
                        onConfirm = {
                            launch {
                                modalBottomSheet.hide()
                                navigator.navigateUp()
                            }
                        }
                    )
                }
            }
        }
    ) {
        when (state.screenType) {
            ScreenType.EMPTY -> Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Gagal memuat soal... :-(",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 50.dp
                        ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color=MaterialTheme.colorScheme.onSurface
                )
            }

            ScreenType.QUIZ -> if (state.questions.isNotEmpty()) {
                ScreenQuiz(
                    quizQuestion = state.questions[state.currentIndex],
                    questionAmount = state.questions.size,
                    response = state.hasAnswer,
                    currentIndex = state.currentIndex,
                    onBackPressed = {
                        onBackPressed()
                    },
                    onClose = {
                        launch {
                            if (state.bottomSheetType != BottomSheetType.CLOSE_CONFIRMATION) {
                                commit { copy(bottomSheetType = BottomSheetType.CLOSE_CONFIRMATION) }
                            }
                            modalBottomSheet.show()
                        }
                    },
                    onSubmitResponse = {
                        commit {
                            copy(
                                visibleButton = true,
                                hasAnswer = it,
                                currentQuestionNumber = state
                                    .questions[state.currentIndex]
                                    .questionNumber
                            )
                        }
                    }
                )
            }

            ScreenType.RESULT -> ScreenQuizResult(
                show = state.showResult,
                state = state.scoreData,
                onNavigateAndReplace = {
                    navigator.navigateAndReplace(it)
                },
                onClose = {
                    onBackPressed()
                }
            )

            ScreenType.LOADING -> Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp)
                )
            }
        }

    }

}

@Preview
@Composable
fun PreviewScreenStartQuiz() {
    BaseMainApp {
        StartQuizScreen(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = StartQuizState()
            )
        )
    }
}