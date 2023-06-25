/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.changePassword

import android.content.Context
import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.ChangePasswordUseCase
import app.trian.mvi.ui.UIEvent
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val changePasswordUseCase: ChangePasswordUseCase
) : MviViewModel<ChangePasswordState, ChangePasswordIntent, ChangePasswordAction>(
    ChangePasswordState()
) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }


    private fun validateData(
        cb: suspend (String) -> Unit
    ) = asyncWithState {

        when {
            newPassword != confirmPassword ->
                sendUiEvent(
                    UIEvent.ShowToast(
                        context.getString(R.string.message_confirm_password_not_match)
                    )
                )

            newPassword.isEmpty() || confirmPassword.isEmpty() ->
                sendUiEvent(
                    UIEvent.ShowToast(
                        context.getString(R.string.message_change_password_field_empty)
                    )
                )

            else -> cb(newPassword)
        }
    }


    override fun onAction(action: ChangePasswordAction) {
        when (action) {
            ChangePasswordAction.Submit -> validateData { newPassword ->
                changePasswordUseCase(newPassword).onEach(
                    success = {
                        hideLoading()
                        sendUiEvent(
                            UIEvent.ShowToast(
                                context.getString(R.string.text_message_success_change_password)
                            )
                        )
                    },
                    error = { message, string ->
                        hideLoading()
                        sendUiEvent(
                            UIEvent.ShowToast(message.ifEmpty { context.getString(string) })
                        )
                    },
                    loading = ::showLoading
                )
            }

        }
    }

}