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
import android.widget.Toast
import app.hilwa.ar.R
import app.hilwa.ar.data.ResultState
import app.hilwa.ar.data.domain.user.ResetPasswordUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : MviViewModel<ResetPasswordState, ResetPasswordAction>(ResetPasswordState()) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(cb: suspend (String) -> Unit) = asyncWithState {
        when {
            email.isEmpty() ->
                showToast(
                    context.getString(R.string.alert_email_empty), Toast.LENGTH_LONG
                )

            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> showToast(
                context.getString(R.string.alert_validation_email), Toast.LENGTH_LONG
            )

            else -> cb(email)
        }
    }


    override fun onAction(action: ResetPasswordAction) {
        when (action) {
            ResetPasswordAction.Submit -> validateData { email ->
                resetPasswordUseCase(email)
                    .collect{
                        when(it){
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
                                    context.getString(R.string.message_success_reset_password),
                                    Toast.LENGTH_LONG
                                )
                                navigateUp()
                            }
                        }
                    }
            }
        }
    }
}