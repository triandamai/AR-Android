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
data class QuizQuestion(
    val id: String = String.Empty,
    val quizId: String = String.Empty,
    val questionNumber: Int = 0,
    val question: String = String.Empty,
    val questionImage: String = "http://via.placeholder.com/640x360",
    val questionOptions: HashMap<String,String> = hashMapOf(),
    val questionCorrectAnswer: String = String.Empty,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
)
