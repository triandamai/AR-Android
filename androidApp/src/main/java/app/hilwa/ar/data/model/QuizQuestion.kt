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

@Keep
data class QuizQuestion(
    val id: String = String.Empty,
    val quizNumber: Int = 0,
    val question: String = String.Empty,
    val image: String = "http://via.placeholder.com/640x360",
    val answer: Map<String, String> = mapOf(),
    val correctAnswer: String = String.Empty,
    val createdAt: String = String.Empty,
    val updatedAt: String = String.Empty
)
