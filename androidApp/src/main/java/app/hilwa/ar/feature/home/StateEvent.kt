/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.hilwa.ar.data.model.HomeMenu
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import app.hilwa.ar.R.drawable
import app.hilwa.ar.base.BaseState
import app.hilwa.ar.feature.auth.signin.SignInState

@Parcelize
@Immutable
data class HomeState(
    val showDialogDeleteTask: Boolean = false,
    val showDropdownMoreOption: Boolean = false,
    val isLoading: Boolean = false,
    val message: String = "Sync...",
) : Parcelable {
}

@Parcelize
@Immutable
data class HomeDataState constructor(
    val menu: @RawValue List<HomeMenu> = listOf(
        HomeMenu(
            name = "Coba AR",
            description = "Lihat macam-macam bagian otak dan ketahui apa " +
                    "saja kegunaan dari setiap bagian",
            image = drawable.ic_start_ar
        ),
        HomeMenu(
            name = "Quiz",
            description = "Uji pemahaman kamu tentang bagian-bagian otak manusia" +
                    " yang sudah kamu pelajari sebelumnya",
            image = drawable.ic_quiz
        ),
        HomeMenu(
            name = "Tentang Aplikasi",
            description = "Ketahui lebih lanjut tentang creator aplikasi",
            image = drawable.ic_about
        )
    )
) :  Parcelable {
}


@Immutable
sealed interface HomeEvent {
}