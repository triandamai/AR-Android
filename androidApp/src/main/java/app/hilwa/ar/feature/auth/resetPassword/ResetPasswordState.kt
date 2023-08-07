/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.resetPassword

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
@Parcelize
data class ResetPasswordState(
    val email: String = String.Empty,

    val isLoading: Boolean = false,
    override val effect: @RawValue ResetPasswordEffect = ResetPasswordEffect.Nothing
) : MviState<ResetPasswordEffect>(), Parcelable