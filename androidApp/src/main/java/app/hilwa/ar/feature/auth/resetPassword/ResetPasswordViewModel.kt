/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.resetPassword

import android.content.Context
import android.util.Patterns
import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.ResetPasswordUseCase
import app.trian.mvi.ui.UIEvent
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : MviViewModel<ResetPasswordState, ResetPasswordIntent, ResetPasswordAction>(ResetPasswordState()) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(cb: suspend (String) -> Unit) = asyncWithState {
        when {
            email.isEmpty() ->
                sendUiEvent(
                    UIEvent.ShowToast(
                        context.getString(R.string.alert_email_empty)
                    )
                )

            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() ->
                sendUiEvent(
                    UIEvent.ShowToast(
                        context.getString(R.string.alert_validation_email)
                    )
                )

            else -> cb(email)
        }
    }


    override fun onAction(action: ResetPasswordAction) {
        when (action) {
            ResetPasswordAction.Submit -> validateData { email ->
                resetPasswordUseCase(email)
                    .onEach(
                        success = {
                            hideLoading()
                            sendUiEvent(
                                UIEvent.ShowToast(
                                    context.getString(R.string.message_success_reset_password)
                                )
                            )
                            sendUiEvent(UIEvent.NavigateUp)
                        },
                        error = { message, string ->
                            hideLoading()
                            sendUiEvent(
                                UIEvent.ShowToast(
                                    message.ifEmpty { context.getString(string) }
                                )
                            )
                        },
                        loading = ::showLoading
                    )
            }
        }
    }
}