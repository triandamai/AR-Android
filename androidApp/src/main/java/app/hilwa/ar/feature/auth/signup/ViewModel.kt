package app.hilwa.ar.feature.auth.signup

import android.content.Context
import android.util.Patterns
import app.hilwa.ar.data.domain.user.SignUpWithEmailAndPasswordUseCase
import app.hilwa.ar.data.utils.ResultState
import app.hilwa.ar.feature.auth.signin.SignIn
import app.hilwa.ar.feature.auth.signup.SignUpEvent.SignUpWithEmail
import app.trian.core.ui.extensions.hideKeyboard
import app.trian.core.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val signUpWithEmailUseCase: SignUpWithEmailAndPasswordUseCase
) : BaseViewModel<SignUpState, SignUpEvent>(context, SignUpState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }
    private fun validateData(cb: suspend (String, String, String) -> Unit) = asyncWithState {
        context.hideKeyboard()
        when {
            email.isEmpty() ||
                    password.isEmpty() ||
                    displayName.isEmpty() -> {
            }//showSnackbar(R.string.message_password_or_email_cannot_empty)

            password != confirmPassword -> {}//showSnackbar(R.string.message_confirm_password_not_match)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
            }//showSnackbar(R.string.alert_validation_email)
            else -> {
                cb(displayName, email, password)
            }
        }
    }

    private fun handleResponse(result: ResultState<Any>) {
        when (result) {
            ResultState.Loading -> showLoading()
            is ResultState.Error -> {
                hideLoading()
                // showSnackbar(result.message)
            }

            is ResultState.Result -> {
                hideLoading()
                // showSnackbar(R.string.text_message_success_register)
                navigation.navigateAndReplace(SignIn.routeName)
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
                )
                    .collect(::handleResponse)
            }

        }
    }

}