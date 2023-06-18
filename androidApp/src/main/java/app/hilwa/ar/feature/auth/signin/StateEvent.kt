package app.hilwa.ar.feature.auth.signin

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.mvi.ui.extensions.Empty
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SignInState(
    val email: String = String.Empty,
    val password: String = String.Empty,
    val isLoading: Boolean = false
) :  Parcelable {

}

@Immutable
sealed interface SignInEvent {
    object SignInWithEmail : SignInEvent
}