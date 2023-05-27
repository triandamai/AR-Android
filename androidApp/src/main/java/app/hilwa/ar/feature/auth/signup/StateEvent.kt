package app.hilwa.ar.feature.auth.signup

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.hilwa.ar.base.extensions.Empty
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SignUpState(
    val displayName: String = String.Empty,
    val email: String = String.Empty,
    val password: String = String.Empty,
    val confirmPassword: String = String.Empty,
    val isLoading:Boolean=false,
    val agreeTnc:Boolean=false
) : Parcelable

@Immutable
sealed interface SignUpEvent {
    object SignUpWithEmail : SignUpEvent

}