package app.hilwa.ar.feature.auth.signup

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.SignUpWithEmailAndPasswordUseCase
import app.hilwa.ar.feature.auth.signin.SignIn
import app.hilwa.ar.feature.auth.signup.SignUpAction.SignUpWithEmail
import app.trian.mvi.ui.UIEvent
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signUpWithEmailUseCase: SignUpWithEmailAndPasswordUseCase
) : MviViewModel<SignUpState, SignUpIntent, SignUpAction>(SignUpState()) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }
    private fun validateData(cb: suspend (String, String, String) -> Unit) = asyncWithState {
        when {
            email.isEmpty()
                    || password.isEmpty()
                    || displayName.isEmpty() ->
                sendUiEvent(
                    UIEvent.ShowToast(
                        context.getString(
                            R.string.message_password_or_email_cannot_empty,
                        )
                    )
                )

            password != confirmPassword ->
                sendUiEvent(
                    UIEvent.ShowToast(
                        context.getString(
                            R.string.message_confirm_password_not_match
                        )
                    )
                )

            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() ->
                sendUiEvent(
                    UIEvent.ShowToast(
                        context.getString(
                            R.string.alert_validation_email
                        )
                    )
                )

            !agreeTnc ->
                sendUiEvent(
                    UIEvent.ShowToast(
                        context.getString(
                            R.string.error_message_agree_tnc
                        )
                    )
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
                ).onEach(
                    success = {
                        hideLoading()
                        sendUiEvent(
                            UIEvent.ShowToast(
                                context.getString(
                                    R.string.text_message_success_register
                                )
                            )
                        )
                        sendUiEvent(UIEvent.NavigateAndReplace(SignIn.routeName))
                    },
                    error = { message, string ->
                        hideLoading()
                        sendUiEvent(
                            UIEvent.ShowToast(
                                message.ifEmpty {
                                    context.getString(
                                        string
                                    )
                                }
                            )
                        )
                    },
                    loading = {
                        showLoading()
                    }
                )
            }

        }
    }

}