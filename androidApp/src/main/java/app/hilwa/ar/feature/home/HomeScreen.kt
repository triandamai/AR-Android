/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.R
import app.hilwa.ar.components.DialogConfirmation
import app.hilwa.ar.components.DialogLoading
import app.hilwa.ar.feature.home.components.ItemFeature
import app.hilwa.ar.feature.home.components.ItemLatestQuiz
import app.hilwa.ar.feature.quiz.detailQuiz.DetailQuiz
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseMainApp
import app.trian.mvi.ui.BaseScreen
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.rememberUIController

object Home {
    const val routeName = "Home"
}

@Navigation(
    route = Home.routeName,
    viewModel = HomeViewModel::class
)
@Composable
internal fun HomeScreen(
    uiContract: UIContract<HomeState, HomeIntent, HomeAction>
) = UIWrapper(uiContract) {

    val modalBottomSheet =
        rememberModalBottomSheetState(initialValue = Hidden, confirmValueChange = {
            when (it) {
                Hidden -> {
                    controller.keyboard.hide()
                    true
                }

                else -> true
            }
        })



    DialogLoading(
        show = state.isLoading,
        title = "Please wait",
        message = state.message
    )
    DialogConfirmation(
        show = state.showDialogDeleteTask,
        message = stringResource(R.string.text_confirmation_delete, ""),
        onConfirm = {
        },
        onCancel = {
            commit { copy(showDialogDeleteTask = false) }
        }
    )

    BaseScreen(
        topAppBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 14.dp,
                            bottomEnd = 14.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Selamat datang",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Hilwa",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dummy_avatar_female),
                        contentDescription = ""
                    )
                }

            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp
                            )
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Take a ")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("test")
                                }
                                append(" and ")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("get reward")
                                }
                            },
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.fillMaxWidth(fraction = 0.6f)
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    LazyRow(
                        content = {
                            items(state.menu) {
                                ItemFeature(
                                    name = it.name,
                                    image = it.image,
                                    onClick = {
                                        navigator.navigateSingleTop(it.route)
                                    }
                                )
                            }
                        }
                    )
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp
                            )
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append("Quiz ")
                                    withStyle(
                                        style = SpanStyle(
                                            color = Color.Black,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    ) {
                                        append("Terbaru")
                                    }
                                },
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.fillMaxWidth(fraction = 0.6f)
                            )
                            Text(
                                text = "Lihat semua",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                items(state.latestQuiz) {
                    ItemLatestQuiz(
                        quizName = it.quizTitle,
                        quizTotalQuestion = "${it.question.size} Soal",
                        quizDuration = "${it.quizDuration} Menit",
                        onClick = {
                            navigator.navigate(
                                DetailQuiz.routeName, it.id
                            )
                        }
                    )
                }
            }
        }
    }


}


@Preview
@Composable
fun PreviewScreenHome() {
    BaseMainApp {
        HomeScreen(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = HomeState()
            )
        )
    }
}