/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.R
import app.trian.mvi.ui.extensions.coloredShadow
import app.trian.mvi.ui.theme.ApplicationTheme

@Composable
fun ItemFeature(
    name: String = "Math",
    description: String = "",
    visibility: Boolean = false,
    @DrawableRes image: Int = R.drawable.ic_start_ar,
    onClick: () -> Unit = {}
) {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val cardWidth = currentWidth - (currentWidth / 3)
    val cardHeight = cardWidth + (currentWidth / 3)

    Box(modifier = Modifier.padding(start = 16.dp)) {
        AnimatedVisibility(
            visible = visibility,
            enter = slideInHorizontally { it / 2 },
            exit = slideOutHorizontally { it / 2 }
        ) {
            Column(
                modifier = Modifier
                    .width(cardWidth)
                    .height(cardHeight)
                    .coloredShadow(
                        color = MaterialTheme.colorScheme.onSurface,
                        alpha = 0.1f
                    )
                    .clip(
                        RoundedCornerShape(
                            16.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(
                        all = 16.dp
                    ),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "",
                    modifier = Modifier.size(cardWidth - 20.dp)
                )
                Button(onClick = onClick) {
                    Text(text = "Coba")
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewItemFeature() {
    ApplicationTheme {
        ItemFeature()
    }
}