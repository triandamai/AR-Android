/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.listQuiz

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class ListQuizState(
    val a: String = ""
) : Parcelable

@Immutable
@Parcelize
data class ListQuizDataState(
    val a: String = ""
) : Parcelable

sealed interface ListQuizEvent {
}