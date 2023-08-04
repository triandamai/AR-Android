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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.components.ButtonSecondary
import app.trian.mvi.ui.extensions.coloredShadow

@Composable
fun BottomBarQuiz(
    show: Boolean = false,
    hasAnswer: String? = null,
    isLastQuestion: Boolean = false,
    onPrev: () -> Unit = {},
    onNext: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = show,
        enter = slideInVertically(initialOffsetY = { it / 2 }),
        exit = slideOutVertically(targetOffsetY = { it / 2 })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .coloredShadow(
                    color = MaterialTheme.colorScheme.onSurface
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                )
                .background(MaterialTheme.colorScheme.surface)
                .padding(
                    top = 25.dp,
                    bottom = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonSecondary(
                text = "Kembali",
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = 150.dp
                    ),
                fullWidth = false,
                onClick = {
                    onPrev()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            ButtonPrimary(
                text = if (isLastQuestion) "Simpan" else "Lanjut",
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = 150.dp
                    ),
                fullWidth = false,
                enabled = hasAnswer != null,
                onClick = {
                    onNext()
                }
            )

        }
    }
}