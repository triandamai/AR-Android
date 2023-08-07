/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.onboard

import android.os.Parcelable
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class OnboardState(
    val isLoading: Boolean = false,
    override val effect: @RawValue OnboardEffect = OnboardEffect.Nothing
) : MviState<OnboardEffect>(), Parcelable