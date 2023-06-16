/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.base.listener.AREventListener
import app.hilwa.ar.components.BottomSheetConfirmation
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.components.ButtonSecondary
import app.hilwa.ar.components.HeaderStepWithProgress
import app.hilwa.ar.components.ItemQuizOption
import app.trian.core.ui.BaseMainApp
import app.trian.core.ui.BaseScreen
import app.trian.core.ui.UIListenerData
import app.trian.core.ui.UiWrapperData
import app.trian.core.ui.extensions.coloredShadow
import app.trian.core.ui.rememberUIController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

object StartQuiz {
    const val routeName = "Quiz"
}

@Composable
internal fun ScreenStartQuiz(
    uiEvent: UIListenerData<StartQuizState, StartQuizDataState, StartQuizEvent, AREventListener>
) = UiWrapperData(uiEvent) {
    LaunchedEffect(key1 = this, block = {
        controller.event.addOnCountDownListener { timeout, data ->
            if (!timeout) {
                commit{ copy(timer = data[0]) }
            } else {
                showSnackbar("Time up!")
            }
        }
    })

    fun onBackPressed() {
        if (state.currentIndex == 0) {
            showBottomSheet()
        } else {
            dispatch(StartQuizEvent.Prev)
        }
    }
    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(
        key1 = this,
        block = {
            if (state.currentIndex == 0) {
                //controller.sendEvent(ScreenToAppEvent().START_TIMER)
            }
        }
    )

    BaseScreen(
        controller = controller,
        bottomBar = {
            AnimatedVisibility(
                visible = state.visibleButton,
                enter = slideInVertically(
                    initialOffsetY = {
                        it / 2
                    },
                ),
                exit = slideOutVertically(
                    targetOffsetY = {
                        it / 2
                    },
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .coloredShadow(
                            color = MaterialTheme.colorScheme.primary
                        )
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(
                            top = 25.dp,
                            bottom = 16.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonSecondary(
                        text = "Kembali",
                        modifier = Modifier
                            .defaultMinSize(
                                minWidth = 150.dp
                            ),
                        fullWidth = false,
                        onClick = {
                            dispatch(StartQuizEvent.Prev)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ButtonPrimary(
                        text = "Lanjut",
                        modifier = Modifier
                            .defaultMinSize(
                                minWidth = 150.dp
                            ),
                        fullWidth = false,
                        enabled = state.hasAnswer != null,
                        onClick = {
                            dispatch(StartQuizEvent.Next)
                        }
                    )

                }
            }

        },
        bottomSheet = {
            BottomSheetConfirmation(
                title = "Yakin keluar dari quiz  ini?",
                message = "Data quiz yang telah kamu isi akan hilang loh",
                textConfirmation = "Keluar",
                textCancel = "Batal",
                onDismiss = {
                    hideBottomSheet()
                },
                onConfirm = {
                    //navigateUp()
                }
            )
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
                                        .data(data.quiz[state.currentIndex].image)
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
                                text = data.quiz[state.currentIndex].question,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    items(data.quiz[state.currentIndex].answer) {
                        ItemQuizOption(
                            selected = it == state.hasAnswer,
                            answer = it,
                            onClick = {
                                commit {
                                    copy(
                                        visibleButton = true,
                                        hasAnswer = it
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
                    total = data.quiz.size,
                    onBackPress = ::onBackPressed,
                    onClose = {
                        showBottomSheet()
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
        ScreenStartQuiz(
            uiEvent = UIListenerData(
                controller = rememberUIController(
                    event = AREventListener()
                ),
                state = StartQuizState(),
                data = StartQuizDataState()
            )
        )
    }
}