/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz

import android.os.Parcelable
import app.hilwa.ar.data.model.ProgressModel
import app.hilwa.ar.data.model.QuizQuestion
import app.hilwa.ar.feature.quiz.startQuiz.component.BottomSheetType
import app.hilwa.ar.feature.quiz.startQuiz.component.ScreenType
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable


@Immutable
@Parcelize
data class StartQuizState(
    //state
    val visibleButton: Boolean = false,
    val currentIndex: Int = 0,
    val currentQuestionNumber: Int = 1,
    val hasAnswer: String? = null,
    val timer: String = String.Empty,
    val bottomSheetType: BottomSheetType = BottomSheetType.CLOSE_CONFIRMATION,
    val response: List<Pair<Int, String>> = listOf(),

    val screenType: ScreenType = ScreenType.LOADING,
    val showResult: Boolean = false,
    //data
    val questions: @RawValue List<QuizQuestion> = listOf(),

    //data
    val scoreData: @RawValue ProgressModel = ProgressModel(),
    override val effect: @RawValue StartQuizEffect = StartQuizEffect.Nothing
) : MviState<StartQuizEffect>(), Parcelable
