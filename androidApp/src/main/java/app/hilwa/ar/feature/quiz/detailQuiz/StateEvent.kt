/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.detailQuiz

import android.os.Parcelable
import app.hilwa.ar.data.model.Quiz
import app.hilwa.ar.data.utils.dummyQuiz
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class DetailQuizState(
    val showContent:Boolean=false
) :  Parcelable {
}

@Immutable
@Parcelize
data class DetailQuizDataState(
    val quiz: @RawValue Quiz = dummyQuiz[0]
) :  Parcelable {
}


sealed interface DetailQuizEvent {
}