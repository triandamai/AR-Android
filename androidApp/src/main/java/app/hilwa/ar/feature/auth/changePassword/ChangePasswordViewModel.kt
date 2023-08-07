/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.changePassword

import android.content.Context
import android.widget.Toast
import app.hilwa.ar.R
import app.hilwa.ar.data.ResultState
import app.hilwa.ar.data.domain.user.ChangePasswordUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val changePasswordUseCase: ChangePasswordUseCase
) : MviViewModel<ChangePasswordState, ChangePasswordAction>(
    ChangePasswordState()
) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }


    private fun validateData(
        cb: suspend (String) -> Unit
    ) = asyncWithState {

        when {
            newPassword != confirmPassword ->
                showToast(
                    context.getString(R.string.message_confirm_password_not_match),
                    Toast.LENGTH_LONG
                )

            newPassword.isEmpty() || confirmPassword.isEmpty() ->
                showToast(
                    context.getString(R.string.message_change_password_field_empty),
                    Toast.LENGTH_LONG
                )

            else -> cb(newPassword)
        }
    }


    override fun onAction(action: ChangePasswordAction) {
        when (action) {
            ChangePasswordAction.Submit -> validateData { newPassword ->
                changePasswordUseCase(newPassword).collect {
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
                            hideLoading()
                            showToast(
                                context.getString(R.string.text_message_success_change_password),
                                Toast.LENGTH_LONG
                            )
                        }
                    }
                }
            }

        }
    }

}