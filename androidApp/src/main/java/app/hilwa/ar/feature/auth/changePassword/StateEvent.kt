package app.hilwa.ar.feature.auth.changePassword

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.hilwa.ar.base.BaseState
import app.hilwa.ar.base.extensions.Empty
import app.hilwa.ar.feature.auth.signin.SignInState
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class ChangePasswordState(
    val newPassword: String = String.Empty,
    val confirmPassword: String = String.Empty,

    val isLoading:Boolean=false
) :  Parcelable {
}


@Immutable
sealed interface ChangePasswordEvent {
    object Submit : ChangePasswordEvent
}