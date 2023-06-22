/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.components.BaseBottomSheet
import app.hilwa.ar.components.BottomSheetConfirmation
import app.hilwa.ar.components.HeaderStepWithProgress
import app.hilwa.ar.components.ItemQuizOption
import app.hilwa.ar.feature.quiz.Quiz
import app.hilwa.ar.feature.quiz.startQuiz.component.BottomBarQuiz
import app.hilwa.ar.feature.quiz.startQuiz.component.BottomSheetType
import app.trian.mvi.Argument
import app.trian.mvi.NavType
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseMainApp
import app.trian.mvi.ui.BaseScreen
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.rememberUIController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

object StartQuiz {
    const val routeName = "Quiz"
    const val argKey = "quizId"
}

@Navigation(
    route = StartQuiz.routeName,
    group=Quiz.routeName,
    viewModel = StartQuizViewModel::class
)
@Argument(
    name = StartQuiz.argKey,
    navType = NavType.String
)
@Composable
internal fun StartQuizScreen(
    uiContract: UIContract<StartQuizState, StartQuizIntent, StartQuizAction>
) = UIWrapper(uiContract) {

    val modalBottomSheet = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            when (state.bottomSheetType) {
                BottomSheetType.TIMEOUT_CONFIRMATION -> false
                BottomSheetType.CLOSE_CONFIRMATION -> true
            }
        }
    )

    LaunchedEffect(key1 = this, block = {
        controller.eventListener.addOnScreenEventListener { event, data ->
            if (event == "updateTimer" && data[0] == "0") {
                commit { copy(timer = data[1]) }
            } else {
                commit { copy(bottomSheetType = BottomSheetType.TIMEOUT_CONFIRMATION) }
                launch {
                    modalBottomSheet.show()
                }
               // controller.bottomSheet.show()
               // controller.snackBar.show("Time up!")
            }
        }
    })

    fun onBackPressed() {
        if (state.currentIndex == 0) launch {  modalBottomSheet.show()}
        else dispatch(StartQuizAction.Prev)
    }
    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(
        key1 = state.currentIndex,
        block = {
            if (state.currentIndex == 0) {
                controller.eventListener.sendEventToApp("START_TIMER")
            }
        }
    )

    BaseScreen(
       modalBottomSheetState = modalBottomSheet,
        bottomBar = {
            BottomBarQuiz(
                show = state.visibleButton,
                isLastQuestion = ((state.quiz.size - 1) == state.currentIndex),
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
                            color = Color.DarkGray
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
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                ),
                content = {
                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                    item {
                        Text(
                            text = state.timer,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(
                                    minHeight = 100.dp
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(state.quiz[state.currentIndex].image)
                                        .build()
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = state.quiz[state.currentIndex].question,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    items(state.quiz[state.currentIndex].answer.toList()) {
                        ItemQuizOption(
                            selected = it.first == state.hasAnswer,
                            answer = it.second,
                            onClick = {
                                commit {
                                    copy(
                                        visibleButton = true,
                                        hasAnswer = it.first
                                    )
                                }
                            }
                        )
                    }
                })

            Column(
                modifier = Modifier.align(
                    Alignment.TopCenter
                )
            ) {
                HeaderStepWithProgress(
                    progress = (state.currentIndex + 1),
                    total = state.quiz.size,
                    onBackPress = ::onBackPressed,
                    onClose = {
                        commit {
                            copy(bottomSheetType = BottomSheetType.CLOSE_CONFIRMATION)
                        }
                        launch {
                            modalBottomSheet.show()
                        }
                    }
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