package app.hilwa.ar.feature.auth.signup

import android.util.Patterns
import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.SignUpWithEmailAndPasswordUseCase
import app.hilwa.ar.feature.auth.signin.SignIn
import app.hilwa.ar.feature.auth.signup.SignUpEvent.SignUpWithEmail
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmailUseCase: SignUpWithEmailAndPasswordUseCase
) : MviViewModel<SignUpState, SignUpEvent>(SignUpState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }
    private fun validateData(cb: suspend (String, String, String) -> Unit) = asyncWithState {
        controller.keyboard.hide()
        when {
            email.isEmpty()
                    || password.isEmpty()
                    || displayName.isEmpty() ->
                controller.snackBar.show(R.string.message_password_or_email_cannot_empty)

            password != confirmPassword ->
                controller.snackBar.show(R.string.message_confirm_password_not_match)

            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() ->  controller.snackBar.show(R.string.alert_validation_email)

            !agreeTnc ->
                controller.snackBar.show(R.string.error_message_agree_tnc)
            else -> {
                cb(displayName, email, password)
            }
        }
    }

    override fun handleActions() = onEvent {
        when (it) {
            SignUpWithEmail -> validateData { displayName, email, password ->
                signUpWithEmailUseCase(
                    displayName = displayName,
                    email = email,
                    password = password
                ).onEach(
                    success = {
                        hideLoading()
                        controller.snackBar.show(R.string.text_message_success_register)
                        controller.navigator.navigateAndReplace(SignIn.routeName)
                    },
                    error = {
                        hideLoading()
                        controller.snackBar.show(it)
                    },
                    loading = {
                        showLoading()
                    }
                )
            }

        }
    }

}