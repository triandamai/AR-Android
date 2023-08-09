/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import app.hilwa.ar.R
import app.hilwa.ar.components.BaseMainApp
import app.hilwa.ar.components.BaseScreen
import app.hilwa.ar.components.DialogConfirmation
import app.hilwa.ar.feature.augmentedReality.ArViewActivity
import app.hilwa.ar.feature.home.components.ItemFeature
import app.hilwa.ar.feature.home.components.ItemLatestQuiz
import app.hilwa.ar.feature.profile.Profile
import app.hilwa.ar.feature.quiz.detailQuiz.DetailQuiz
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.internal.contract.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

object Home {
    const val routeName = "Home"
}

@Navigation(
    route = Home.routeName,
    viewModel = HomeViewModel::class
)
@Composable
internal fun HomeScreen(
    uiContract: UIContract<HomeState, HomeAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract) {

    val view = LocalView.current
    val ctx = LocalContext.current
    val currentWindow = (view.context as? Activity)?.window
    val primary = MaterialTheme.colorScheme.primary.toArgb()
    val surface = MaterialTheme.colorScheme.surface.toArgb()
    val profileCorner = RoundedCornerShape(10.dp)

    fun changeStatusBar(isLeave: Boolean) {
        (view.context as Activity).window.statusBarColor = if (isLeave) primary else surface
        WindowCompat.getInsetsController(currentWindow!!, view).isAppearanceLightStatusBars =
            !isLeave
    }

    if (!view.isInEditMode) {
        /* getting the current window by tapping into the Activity */
        SideEffect {
            changeStatusBar(true)
        }
    }



    LaunchedEffect(key1 = uiContract, block = {
        commit { copy(isLoadingFeature = false) }
    })
    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            changeStatusBar(false)
        }
    })

    DialogConfirmation(
        show = state.showDialogDeleteTask,
        message = stringResource(R.string.text_confirmation_delete, ""),
        onConfirm = {
        },
        onCancel = {
            commit { copy(showDialogDeleteTask = false) }
        }
    )

    LaunchedEffect(key1 = Unit, block = {
        dispatch(HomeAction.GetLatestData)
    })
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
                        text = state.fullName.ifEmpty { "Tanpa Nama" },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(profileCorner)
                        .clickable(
                            enabled = true,
                            onClick = {
                                controller.navigator.navigate(Profile.routeName)
                            }
                        )
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(ctx)
                                .data(state.profilePicture)
                                .placeholder(R.drawable.dummy_avatar_female)
                                .error(R.drawable.dummy_avatar_female)
                                .build()
                        ),
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
                                append("Coba fitur ")
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("AR")
                                }
                                append("-nya dan kerjakan ")
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Quiz")
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
                                        if (it.route == String.Empty) {
                                            Intent(ctx, ArViewActivity::class.java).apply {
                                                ctx.startActivity(this)
                                            }
                                            //navigator.navigate(ArView.routeName)
                                            // controller.toast.show("Coming soon")
                                        } else {
                                            navigator.navigateSingleTop(it.route)
                                        }
                                    },
                                    visibility = !state.isLoadingFeature
                                )
                            }
                        }
                    )
                }
                item {
//                    Column(
//                        horizontalAlignment = Alignment.Start,
//                        verticalArrangement = Arrangement.Top,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                horizontal = 20.dp
//                            )
//                    ) {
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Row(
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Text(
//                                text = buildAnnotatedString {
//                                    append("Quiz ")
//                                    withStyle(
//                                        style = SpanStyle(
//                                            color = MaterialTheme.colorScheme.primary,
//                                            fontWeight = FontWeight.SemiBold
//                                        )
//                                    ) {
//                                        append("Terbaru")
//                                    }
//                                },
//                                style = MaterialTheme.typography.headlineSmall,
//                                modifier = Modifier.fillMaxWidth(fraction = 0.6f)
//                            )
//                            Text(
//                                text = "Lihat semua",
//                                fontWeight = FontWeight.SemiBold
//                            )
//                        }
//                        Spacer(modifier = Modifier.height(16.dp))
//                    }
                }
//                if (state.latestQuiz.isEmpty() && !state.isLoadingLatestQuiz) {
//                    item {
//                        Text(
//                            text = "Yaah, belum ada quiz buat kamu nih",
//                            fontWeight = FontWeight.SemiBold,
//                            modifier = Modifier.fillMaxWidth(),
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//                if (state.isLoadingLatestQuiz) {
//                    item {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 20.dp),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//                            CircularProgressIndicator()
//                        }
//                    }
//                }
                items(state.latestQuiz) {
                    ItemLatestQuiz(
                        quizName = it.quizTitle,
                        quizTotalQuestion = "${it.totalQuestion} Soal",
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