/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.hilwa.ar.ui.ApplicationTheme
import app.hilwa.ar.ui.baseShapes
import app.hilwa.ar.ui.baseTypography
import app.hilwa.ar.ui.darkColors
import app.hilwa.ar.ui.lightColors
import app.trian.mvi.ui.internal.UIController
import app.trian.mvi.ui.internal.rememberUIController

@Composable
fun BaseMainApp(
    controller: UIController = rememberUIController(),
    dynamicColor: Boolean = true,
    lightColor: ColorScheme = lightColors,
    darkColor: ColorScheme = darkColors,
    typography: androidx.compose.material3.Typography = baseTypography,
    shapes: Shapes = baseShapes,
    content: @Composable (appState: UIController) -> Unit = { }
) {
    ApplicationTheme(
        dynamicColor = dynamicColor,
        lightColor = lightColor,
        darkColor = darkColor,
        typography = typography,
        shapes = shapes,
    ) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            content.invoke(controller)
        }
    }
}