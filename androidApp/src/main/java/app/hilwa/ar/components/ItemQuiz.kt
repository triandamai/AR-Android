/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.theme.MyApplicationTheme
import app.hilwa.ar.R.drawable
import app.trian.core.ui.extensions.Empty

@Composable
fun ItemQuiz(
    quizName: String = String.Empty,
    @DrawableRes quizImage: Int = drawable.ic_onboard,
    quizProgress: Int = 0,
    quizAmountQuestion:Int=0,
    onClick:()->Unit={}
) {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density

    val cardWidth = currentWidth / 2 - 20.dp
    Box(
        modifier = Modifier.padding(
            all = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .size(cardWidth)
                .clip(MaterialTheme.shapes.medium)
                .clickable(
                    enabled = true,
                    onClick=onClick
                )
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(
                    all = 10.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = quizImage),
                contentDescription = "",
                modifier = Modifier.size(
                    cardWidth / 2
                ),
                contentScale = ContentScale.Fit
            )
            Text(
                text = quizName,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            LinearProgressIndicator(
                progress = ((quizProgress.toFloat() / quizAmountQuestion) * 100) / 100,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                strokeCap = StrokeCap.Round,
            )

        }
    }
}

@Preview
@Composable
fun PreviewItemQuiz() {
    MyApplicationTheme {
        ItemQuiz(
            quizName = "Bagian-Bagian Otak Manusia"
        )
    }
}