/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.resultQuiz

import android.os.Parcelable
import app.hilwa.ar.data.model.ProgressModel
import app.trian.mvi.ui.extensions.Empty
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class ResultQuizState(
    val isLoading: Boolean = true,

    //data
    val scoreData:@RawValue ProgressModel= ProgressModel()
) : Parcelable
