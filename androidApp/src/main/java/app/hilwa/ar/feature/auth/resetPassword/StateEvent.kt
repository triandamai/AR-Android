package app.hilwa.ar.feature.auth.resetPassword

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.hilwa.ar.base.extensions.Empty
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class ResetPasswordState(
    val email: String = String.Empty,

    val isLoading:Boolean=false
) : Parcelable

@Immutable
sealed interface ResetPasswordEvent {
    object Submit : ResetPasswordEvent
}