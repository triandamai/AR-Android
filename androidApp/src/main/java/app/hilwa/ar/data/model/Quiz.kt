/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.model

import app.trian.mvi.ui.extensions.Empty
import com.google.errorprone.annotations.Keep
import com.google.firebase.Timestamp

@Keep
data class Quiz(
    val id: String = String.Empty,
    val quizImage: String = "http://via.placeholder.com/640x360",
    val quizTitle: String = String.Empty,
    val quizDescription: String = String.Empty,
    val quizDuration: Long = 0L,
    val progress: Int = 0,
    val totalQuestion: Int = 0,
    val question: List<QuizQuestion> = listOf(),
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)
