package app.hilwa.ar.feature.auth.signup

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import app.hilwa.ar.R
import app.hilwa.ar.data.ResultState
import app.hilwa.ar.data.domain.user.SignUpWithEmailAndPasswordUseCase
import app.hilwa.ar.feature.auth.signin.SignIn
import app.hilwa.ar.feature.auth.signup.SignUpAction.SignUpWithEmail
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signUpWithEmailUseCase: SignUpWithEmailAndPasswordUseCase
) : MviViewModel<SignUpState, SignUpAction>(SignUpState()) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }
    private fun validateData(cb: suspend (String, String, String) -> Unit) = asyncWithState {
        when {
            email.isEmpty()
                    || password.isEmpty()
                    || displayName.isEmpty() ->
                showToast(
                    context.getString(
                        R.string.message_password_or_email_cannot_empty,
                    ),
                    Toast.LENGTH_LONG
                )

            password != confirmPassword ->
                showToast(
                    context.getString(R.string.message_confirm_password_not_match),
                    Toast.LENGTH_LONG
                )

            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() ->
                showToast(
                    context.getString(
                        R.string.alert_validation_email
                    ),
                    Toast.LENGTH_LONG
                )

            !agreeTnc

            ->
                showToast(
                    context.getString(
                        R.string.error_message_agree_tnc
                    ),
                    Toast.LENGTH_LONG
                )

            else -> {
                cb(displayName, email, password)
            }
        }
    }

    override fun onAction(action: SignUpAction) {
        when (action) {
            SignUpWithEmail -> validateData { displayName, email, password ->
                signUpWithEmailUseCase(
                    displayName = displayName,
                    email = email,
                    password = password
                ).collect {
                    when (it) {
                        is ResultState.Error -> {
                            hideLoading()
                            showToast(
                                it.message.ifEmpty { context.getString(it.stringId) },
                                Toast.LENGTH_LONG
                            )
                        }
                        ResultState.Loading -> showLoading()
                        is ResultState.Result -> {
                            showToast(
                                context.getString(
                                    R.string.text_message_success_register
                                ),
                                Toast.LENGTH_LONG
                            )
                            hideLoading()
                            navigateAndReplace(SignIn.routeName)
                        }
                    }
                }
            }

        }
    }

}