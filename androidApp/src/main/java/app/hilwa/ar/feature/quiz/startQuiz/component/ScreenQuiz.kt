/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz.component

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
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.components.HeaderStepWithProgress
import app.hilwa.ar.components.ItemQuizOption
import app.hilwa.ar.data.model.QuizQuestion
import app.hilwa.ar.ui.ApplicationTheme
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ScreenQuiz(
    quizQuestion:QuizQuestion = QuizQuestion(),
    questionAmount:Int=0,
    response:String?=null,
    currentIndex:Int?=null,
    onBackPressed:()->Unit={},
    onClose:()->Unit={},
    onSubmitResponse:(String)->Unit={}
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
                                        .data(quizQuestion.questionImage)
                                        .build()
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth(
                                        fraction = 0.6f
                                    )
                                    .height(250.dp),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = quizQuestion.question,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                color=MaterialTheme.colorScheme.onSurface
                            )
                    }
                }

                    items(quizQuestion.questionOptions.toList()) {
                        ItemQuizOption(
                            selected = it.first == response,
                            answer = it.second,
                            onClick = {
                                onSubmitResponse(it.first)
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
                progress = ((currentIndex ?: 0) + 1),
                total = questionAmount,
                onBackPress = { onBackPressed() },
                onClose = {
                    onClose()
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewScreenQuiz() {
    ApplicationTheme {
        ScreenQuiz()
    }
}