/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.signup

import android.os.Parcelable
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class SignUpState(
    val displayName: String = String.Empty,
    val email: String = String.Empty,
    val password: String = String.Empty,
    val confirmPassword: String = String.Empty,
    val isLoading: Boolean = false,
    val agreeTnc: Boolean = false,
    override val effect: @RawValue SignUpEffect = SignUpEffect.Nothing
) : MviState<SignUpEffect>(), Parcelable