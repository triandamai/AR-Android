/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.listQuiz

import android.os.Parcelable
import app.hilwa.ar.base.BaseState
import app.hilwa.ar.data.model.Quiz
import app.hilwa.ar.data.utils.dummyQuiz
import app.hilwa.ar.feature.auth.signin.SignInState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class ListQuizState(
    val a: String = ""
) :  Parcelable {
}


@Immutable
@Parcelize
data class ListQuizDataState(
    val quiz: @RawValue List<Quiz> = dummyQuiz
) :  Parcelable {
}


sealed interface ListQuizEvent {
}