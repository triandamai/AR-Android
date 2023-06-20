/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.R
import app.trian.mvi.ui.theme.ApplicationTheme

@Composable
fun ItemLatestQuiz(
    quizName: String = "Pengenalan bagian bagian otak",
    quizDuration:String="",
    quizTotalQuestion:String="",
    onClick:()->Unit={}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        12.dp
                    )
                )
                .clickable(
                    enabled = true,
                    onClick = onClick
                )
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummy_question_quiz),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = quizName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = quizTotalQuestion,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .height(12.dp)
                            .width(2.dp)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = quizDuration,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewItemLatestQuiz() {
    ApplicationTheme {
        ItemLatestQuiz(
            quizDuration = "60 Menit",
            quizTotalQuestion = "100 Soal"
        )
    }
}