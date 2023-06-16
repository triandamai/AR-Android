/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.detailQuiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import app.hilwa.ar.components.BottomSheetConfirmation
import app.hilwa.ar.components.ButtonPrimary
import app.trian.core.annotation.Navigation
import app.trian.core.ui.BaseMainApp
import app.trian.core.ui.BaseScreen
import app.trian.core.ui.UIListenerData
import app.trian.core.ui.UIWrapper
import app.trian.core.ui.rememberUIController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.delay

object DetailQuiz {
    const val routeName = "DetailQuiz"
    const val argKey = "quizId"
    fun routeName() = routeName.plus("/")
        .plus(argKey)

    val navArg = listOf(
        navArgument(argKey) {
            type = NavType.StringType
        }
    )


}

@Navigation(
    route = DetailQuiz.routeName,
    arguments=[DetailQuiz.argKey],
    viewModel = DetailQuizViewModel::class
)
@Composable
internal fun ScreenDetailQuiz(
    uiEvent: UIListenerData<DetailQuizState, DetailQuizDataState, DetailQuizEvent>
) = UIWrapper(uiEvent) {

    LaunchedEffect(key1 = this, block = {
        delay(1000)
        commit {
            copy(
                showContent = true
            )
        }
    })

    BaseScreen(
        controller = controller,
        bottomSheet = {
            BottomSheetConfirmation(
                title = "Sudah siap?",
                message = "Kamu akan mengerjakan quiz ini, tekan lanjutkan untuk mengerjakan",
                textConfirmation = "Oke",
                textCancel = "Batal",
                onDismiss = {
                    hideBottomSheet()
                },
                onConfirm = {
                    hideBottomSheet()
                    //navigateSingleTop(StartQuiz.routeName)
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            IconButton(
                onClick = {
                    // navigateUp()
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = ""
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = 60.dp,
                        horizontal = 30.dp
                    )
            ) {
                AnimatedVisibility(
                    visible = state.showContent,
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(
                                    10.dp
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(
                                all = 16.dp
                            ),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
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
                                        .data(data.quiz.quizImage)
                                        .crossfade(true)
                                        .build()
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(
                                        fraction = 0.4f
                                    ),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = data.quiz.quizDescription,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        ButtonPrimary(
                            text = "Kerjakan Sekarang",
                            onClick = {
                                showBottomSheet()
                            }
                        )
                    }
                }


            }
        }
    }
}

@Preview
@Composable
fun PreviewScreenDetailQuiz() {
    BaseMainApp {
        ScreenDetailQuiz(
            uiEvent = UIListenerData(
                controller = rememberUIController(),
                state = DetailQuizState(),
                data = DetailQuizDataState()
            )
        )
    }
}