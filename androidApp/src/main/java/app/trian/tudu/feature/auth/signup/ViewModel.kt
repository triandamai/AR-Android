package app.trian.tudu.feature.auth.signup

import android.util.Patterns
import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.sdk.auth.AuthSDK
import app.trian.tudu.data.utils.Response
import app.trian.tudu.feature.auth.signin.SignIn
import app.trian.tudu.feature.auth.signup.SignUpEvent.SignUpWithEmail
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authSDK: AuthSDK
) : BaseViewModel<SignUpState, SignUpEvent>(SignUpState()) {
    init {
        handleActions()
    }

    private fun validateData(cb: suspend (String, String, String) -> Unit) = async {
        with(uiState.value) {
            when {
                email.isEmpty() ||
                        password.isEmpty() ||
                        displayName.isEmpty() -> showSnackbar(R.string.message_password_or_email_cannot_empty)

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> showSnackbar(R.string.alert_validation_email)
                else -> {
                    cb(displayName, email, password)
                }
            }

        }
    }

    private fun handleResponse(result: Response<FirebaseUser?>) {
        when (result) {
            is Response.Error -> showSnackbar(result.message)
            Response.Loading -> Unit
            is Response.Result -> navigateAndReplaceAll(SignIn.routeName)
        }
    }

    override fun handleActions() = onEvent {
        when (it) {
            SignUpWithEmail -> validateData { displayName, email, password ->
                authSDK.registerWithEmail(displayName = displayName, email = email, password = password)
                    .catch { }
                    .collect(::handleResponse)
            }

        }
    }

}