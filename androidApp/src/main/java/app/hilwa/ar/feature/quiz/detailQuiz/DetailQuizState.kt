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
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class DetailQuizState(
    val quizId: String = "",
    val quiz: @RawValue Quiz = Quiz(),
    val isLoading: Boolean = true,
    override val effect: @RawValue DetailQuizEffect = DetailQuizEffect.Nothing
) : MviState<DetailQuizEffect>(), Parcelable

