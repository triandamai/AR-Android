/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.resultQuiz

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.R
import app.hilwa.ar.feature.home.Home
import app.hilwa.ar.feature.quiz.Quiz
import app.trian.mvi.Argument
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseScreen
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController
import app.trian.mvi.ui.theme.ApplicationTheme

object ResultQuiz {
    const val routeName = "ResultQuiz"
    const val argKey = "quizId"
}

@Navigation(
    route = ResultQuiz.routeName,
    viewModel = ResultQuizViewModel::class
)
@Argument(
    name = ResultQuiz.argKey
)
@Composable
fun ResultQuizScreen(
    uiContract: UIContract<ResultQuizState, ResultQuizIntent, ResultQuizAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract = uiContract) {
    val currentWidth = LocalContext.current
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val imageWidth = currentWidth - (currentWidth / 3)
    BackHandler {
        navigator.navigateAndReplace(Home.routeName)
    }

    BaseScreen() {
        Box(modifier = Modifier.fillMaxSize()) {

            //
            AnimatedVisibility(visible = !state.isLoading) {
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
                    Column {
                        Text(
                            text = "Selamat!",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Kamu sudah menyelesaikan quiz ini, berikut adalah hasil score dari quiz yang sudah kamu kerjakan.",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Skor Kamu",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "20/100",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal
                        )

                        Text(
                            text = "Jawaban benenar",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "20",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal
                        )
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
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Outlined.Close, contentDescription = "")
            }
        }
    }
}

@Preview
@Composable
fun PreviewResultQuizScreen() {
    ApplicationTheme {
        ResultQuizScreen(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = ResultQuizState()
            )
        )
    }
}