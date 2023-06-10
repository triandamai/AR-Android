/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.hilwa.ar.R
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.BaseScreen
import app.hilwa.ar.base.UIWrapper
import app.hilwa.ar.base.UIWrapperListenerData
import app.hilwa.ar.components.DialogConfirmation
import app.hilwa.ar.components.DialogLoading
import app.hilwa.ar.components.ItemHome
import app.hilwa.ar.feature.quiz.listQuiz.ListQuiz
import app.hilwa.ar.rememberUIController

object Home {
    const val routeName = "Home"
}

@Composable
internal fun ScreenHome(
    state: HomeState = HomeState(),
    dataState: HomeDataState = HomeDataState(),
    invoker: UIWrapperListenerData<HomeState, HomeDataState, HomeEvent>
) = UIWrapper(invoker = invoker) {

    LaunchedEffect(key1 = this, block = {
        addOnBottomSheetStateChangeListener {
            if (it == Hidden) {
                hideKeyboard()
            }
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

    BaseScreen() {
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
                            text = stringResource(R.string.title_home),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(R.string.subtitle_home, "Hilwa"),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            textAlign = TextAlign.Start
                        )
                    }
                }
                items(dataState.menu) { data ->
                    ItemHome(
                        name = data.name,
                        description = data.description,
                        image = data.image,
                        onClick = {
                            navigateSingleTop(ListQuiz.routeName)
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
        ScreenHome(
            invoker = UIWrapperListenerData(
                controller = rememberUIController(),
                state = HomeState(),
                data = HomeDataState()
            )
        )
    }
}