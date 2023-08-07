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
import app.hilwa.ar.R.drawable
import app.hilwa.ar.data.model.HomeMenu
import app.hilwa.ar.data.model.Quiz
import app.hilwa.ar.feature.about.About
import app.hilwa.ar.feature.quiz.listQuiz.ListQuiz
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Immutable
data class HomeState(
    val showDialogDeleteTask: Boolean = false,
    val showDropdownMoreOption: Boolean = false,
    val isLoadingFeature: Boolean = true,
    val isLoadingLatestQuiz: Boolean = true,
    val message: String = "Sync...",
    val profilePicture: String = String.Empty,

    //data
    val fullName: String = String.Empty,
    val menu: @RawValue List<HomeMenu> = listOf(
        HomeMenu(
            name = "Coba AR",
            description = "Lihat macam-macam bagian otak dan ketahui apa " +
                    "saja kegunaan dari setiap bagian",
            image = drawable.ic_start_ar,
            route = String.Empty
        ),
        HomeMenu(
            name = "Quiz",
            description = "Uji pemahaman kamu tentang bagian-bagian otak manusia" +
                    " yang sudah kamu pelajari sebelumnya",
            image = drawable.ic_quiz,
            route = ListQuiz.routeName
        ),
        HomeMenu(
            name = "Tentang Aplikasi",
            description = "Ketahui lebih lanjut tentang creator aplikasi",
            image = drawable.ic_about,
            route = About.routeName
        )
    ),
    val latestQuiz: @RawValue List<Quiz> = listOf(),
    override val effect: @RawValue HomeEffect = HomeEffect.Nothing
) : MviState<HomeEffect>(), Parcelable

