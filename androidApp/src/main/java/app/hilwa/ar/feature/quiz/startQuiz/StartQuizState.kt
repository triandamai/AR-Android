/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz

import android.os.Parcelable
import app.hilwa.ar.data.model.QuizQuestion
import app.hilwa.ar.feature.quiz.startQuiz.component.BottomSheetType
import app.trian.mvi.ui.extensions.Empty
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
    val isLoading: Boolean = false,
    val response: List<Pair<Int, String>> = listOf(),
    //data
    val questions: @RawValue List<QuizQuestion> = listOf()
) : Parcelable
