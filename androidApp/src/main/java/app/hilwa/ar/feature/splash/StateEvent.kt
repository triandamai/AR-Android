package app.hilwa.ar.feature.splash

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.hilwa.ar.base.BaseState
import app.hilwa.ar.base.extensions.Empty
import app.hilwa.ar.feature.auth.signin.SignInState
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