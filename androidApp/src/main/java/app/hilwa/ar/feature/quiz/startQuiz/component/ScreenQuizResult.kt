/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.R
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.data.model.ProgressModel
import app.hilwa.ar.feature.home.Home
import app.hilwa.ar.feature.quiz.listQuiz.ListQuiz
import app.trian.mvi.ui.theme.ApplicationTheme

@Composable
fun ScreenQuizResult(
    show:Boolean=false,
    state: ProgressModel=ProgressModel(),
    onNavigateAndReplace:(String)->Unit={},
    onClose:()->Unit={}
) {
    val currentWidth = LocalContext.current
            .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val imageWidth = currentWidth - (currentWidth / 3)

    val scoreColor = when {
        state.quizScore <= 30 -> Color.Red
        state.quizScore <= 40 -> Color.Yellow
        state.quizScore <= 70 -> Color.Blue
        state.quizScore <= 100 -> Color.Green
        else -> Color.Red
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = show,
            enter = slideInVertically(initialOffsetY = { it / 2 }),
            exit = slideOutVertically(targetOffsetY = { it / 2 })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_trophy),
                    contentDescription = "",
                    modifier = Modifier.size(imageWidth)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.title_result_quiz),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.subtitle_result_quiz),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.subtitle_result_quiz_score),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.headlineSmall.toSpanStyle()
                                    .copy(
                                        color = scoreColor,
                                        fontWeight = FontWeight.Medium
                                    ),
                            ) {
                                append(state.quizScore.toString())
                            }
                            withStyle(
                                style = MaterialTheme.typography.headlineSmall.toSpanStyle()
                                    .copy(
                                        fontWeight = FontWeight.Medium
                                    ),
                            ) {
                                append("/")
                                append("100 Point")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(R.string.subtitle_result_quiz_amount_right_answer),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.headlineSmall.toSpanStyle()
                                    .copy(
                                        color = scoreColor,
                                        fontWeight = FontWeight.Medium
                                    ),
                            ) {
                                append(state.amountRightAnswer.toString())
                            }
                            withStyle(
                                style = MaterialTheme.typography.headlineSmall.toSpanStyle()
                                    .copy(
                                        fontWeight = FontWeight.Medium
                                    ),
                            ) {
                                append("/")
                                append(state.amountQuestion.toString())
                                append(" Soal")
                            }
                        },
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ButtonPrimary(text = "Kerjakan Quiz lagi") {
                        onNavigateAndReplace(Home.routeName)
                    }
                }

            }
        }

        //top app bar and button close
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hasil Quiz",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = {
                onClose()
            }
        ) {
            Icon(imageVector = Icons.Outlined.Close, contentDescription = "")
        }
    }
}

@Preview
@Composable
fun PreviewScreenQuizResult() {
    ApplicationTheme {
        ScreenQuizResult()
    }
}