/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.profile

import android.graphics.Bitmap
import android.os.Parcelable
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class ProfileState(
    val email: String = String.Empty,
    val displayName: String = String.Empty,
    val profilePicture: String = String.Empty,

    val inputEmail: String = String.Empty,
    val inputDisplayName: String = String.Empty,

    val isEdit: Boolean = false,
    val bitmap: Bitmap? = null,
    override val effect: @RawValue ProfileEffect = ProfileEffect.Nothing
) : MviState<ProfileEffect>(), Parcelable
