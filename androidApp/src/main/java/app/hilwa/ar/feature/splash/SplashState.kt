package app.hilwa.ar.feature.splash

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.internal.contract.MviState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
@Parcelize
data class SplashState(
    var params: String = String.Empty,
    override val effect: @RawValue SplashEffect = SplashEffect.Nothing,
) : MviState<SplashEffect>(), Parcelable
