package app.hilwa.ar.feature.splash

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.mvi.ui.extensions.Empty
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SplashState(
    var params:String=String.Empty,
) :  Parcelable
