/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.model

import androidx.annotation.Keep
import app.trian.mvi.ui.extensions.Empty
import com.google.firebase.Timestamp

@Keep
data class ProgressModel(
    val quizId: String = String.Empty,
    val questionResponse: HashMap<String, String> = hashMapOf(),
    val quizScore: Int = 0,
    val amountRightAnswer:Int=0,
    val amountQuestion:Int=0,
    val createdAt: Timestamp = Timestamp.now(),
    val updateAt: Timestamp = Timestamp.now()
)
