package app.hilwa.ar.feature.auth.onboard

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.hilwa.ar.base.BaseState
import app.hilwa.ar.feature.auth.signin.SignInState
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class OnboardState(
    val isLoading: Boolean = false
): Parcelable {
}


@Immutable
sealed interface OnboardEvent {
    object SignInWithGoogle : OnboardEvent
}