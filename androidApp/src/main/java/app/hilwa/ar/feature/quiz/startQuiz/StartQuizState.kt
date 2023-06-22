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
import app.hilwa.ar.data.utils.dummyQuiz
import app.hilwa.ar.feature.quiz.startQuiz.component.BottomSheetType
import app.trian.mvi.ui.extensions.Empty
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable




@Immutable
@Parcelize
data class StartQuizState(
    val visibleButton: Boolean = false,
    val currentIndex: Int = 0,
    val hasAnswer: String? = null,
    val timer: String = String.Empty,
    val bottomSheetType: BottomSheetType = BottomSheetType.TIMEOUT_CONFIRMATION,
    val quiz: @RawValue List<QuizQuestion> = dummyQuiz[0].question
) : Parcelable
