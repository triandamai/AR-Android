package app.hilwa.ar.feature.auth.resetPassword

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.hilwa.ar.base.BaseState
import app.hilwa.ar.base.extensions.Empty
import app.hilwa.ar.feature.auth.signin.SignInState
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class ResetPasswordState(
    val email: String = String.Empty,

    val isLoading:Boolean=false
) : Parcelable {
}


@Immutable
sealed interface ResetPasswordEvent {
    object Submit : ResetPasswordEvent
}