/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.listQuiz

import android.os.Parcelable
import app.hilwa.ar.data.model.Quiz
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class ListQuizState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false,
    val quiz: @RawValue List<Quiz> = listOf(),
    override val effect: @RawValue ListQuizEffect = ListQuizEffect.Nothing
) : MviState<ListQuizEffect>(), Parcelable

