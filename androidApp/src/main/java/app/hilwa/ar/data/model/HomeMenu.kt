/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.model

import app.hilwa.ar.R.drawable
import app.trian.mvi.ui.extensions.Empty
import com.google.errorprone.annotations.Keep

@Keep
data class HomeMenu(
    val name: String = "",
    val description: String = "",
    val image: Int = drawable.ic_about,
    val route: String = String.Empty
)
