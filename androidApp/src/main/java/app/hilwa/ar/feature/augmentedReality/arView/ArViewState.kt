/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.augmentedReality.arView

import android.os.Parcelable
import app.trian.mvi.ui.extensions.Empty
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class ArViewState(
    val a: String = String.Empty,
) : Parcelable
