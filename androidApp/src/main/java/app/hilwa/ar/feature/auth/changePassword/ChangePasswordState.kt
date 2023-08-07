/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.changePassword

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
@Parcelize
data class ChangePasswordState(
    val newPassword: String = String.Empty,
    val confirmPassword: String = String.Empty,

    val isLoading: Boolean = false,
    override val effect: @RawValue ChangePasswordEffect = ChangePasswordEffect.Nothing
) : MviState<ChangePasswordEffect>(), Parcelable