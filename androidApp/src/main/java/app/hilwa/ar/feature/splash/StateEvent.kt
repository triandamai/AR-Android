package app.hilwa.ar.feature.splash

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.mvi.ui.extensions.Empty
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SplashUiState(
    var params:String=String.Empty
) :  Parcelable {
}

@Immutable
@Parcelize
data class SplashUiDataState(
    var params:String=String.Empty
) :  Parcelable {
}

sealed interface SplashEvent{
    object CheckSession:SplashEvent
}