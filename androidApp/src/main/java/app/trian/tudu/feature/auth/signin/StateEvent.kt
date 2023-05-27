package app.trian.tudu.feature.auth.signin

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading:Boolean=false
) : Parcelable

@Immutable
sealed class SignInEvent {
    object SignInWithEmail : SignInEvent()
}