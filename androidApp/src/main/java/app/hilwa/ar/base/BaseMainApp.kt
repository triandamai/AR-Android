/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.hilwa.ar.base

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.ApplicationState
import app.hilwa.ar.data.theme.ThemeData
import app.hilwa.ar.rememberApplicationState
import app.hilwa.ar.theme.MyApplicationTheme

@Composable
fun BaseMainApp(
    appState: ApplicationState = rememberApplicationState(),
    content: @Composable (appState: ApplicationState) -> Unit = { }
) {
    MyApplicationTheme() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            content.invoke(appState)
        }
    }
}

@Composable
fun BaseScreen(
    appState: ApplicationState = rememberApplicationState(),
    topAppBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    bottomSheet: @Composable () -> Unit = { },
    content: @Composable (appState: ApplicationState) -> Unit = { }
) {
    ModalBottomSheetLayout(
        sheetContent = {
            Column(
                Modifier
                    .defaultMinSize(
                        minHeight = 50.dp
                    )
                    .wrapContentHeight()
            ) {
                bottomSheet()
            }
        },
        sheetState = appState.bottomSheetState,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetShape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp
        )
    ) {
        Scaffold(
            topBar = topAppBar,
            bottomBar = bottomBar,
            snackbarHost = {
                SnackbarHost(
                    hostState = appState.snackbarHostState,
                    snackbar = { appState.snackbar.invoke(it) })
            },
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            contentWindowInsets = WindowInsets.ime
        ) {
            Column(
                modifier = Modifier.padding(it)
            ) {
                content(appState)
            }
        }
    }
}

@Preview
@Composable
fun PreviewBaseMainApp() {
    BaseMainApp()
}