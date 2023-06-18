/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.mvi.ui.extensions.coloredShadow
import app.trian.mvi.ui.theme.ApplicationTheme

@Composable
fun ItemFeature() {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val currentHeight = ctx
        .resources
        .displayMetrics.heightPixels.dp /
            LocalDensity.current.density
    val cardWidth = currentWidth - (currentWidth / 3)
    val cardHeight = currentHeight - (currentHeight / 3)

    Column(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .coloredShadow(
                MaterialTheme.colorScheme.primary
            )
            .clip(
                RoundedCornerShape(
                    14.dp
                )
            )
            .background(MaterialTheme.colorScheme.surface)
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
                append(" and")
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("get reward")
                }
            }
        )

    }
}

@Preview
@Composable
fun PreviewItemFeature() {
    ApplicationTheme {
        ItemFeature()
    }
}