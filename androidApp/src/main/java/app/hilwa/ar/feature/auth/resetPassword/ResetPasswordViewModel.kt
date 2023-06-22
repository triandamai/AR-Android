/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.resetPassword

import android.util.Patterns
import app.hilwa.ar.data.domain.user.ResetPasswordUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : MviViewModel<ResetPasswordState,ResetPasswordIntent, ResetPasswordAction>(ResetPasswordState()) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(cb: suspend (String) -> Unit) = asyncWithState {
        when {
            email.isEmpty() -> {}//controller.snackBar.show(R.string.alert_email_empty)
            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> {}//controller.snackBar.show(R.string.alert_validation_email)

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
                            //controller.snackBar.show(R.string.message_success_reset_password)
                            //controller.navigator.navigateUp()
                        },
                        error = {_,_->
                            hideLoading()
                            // controller.snackBar.show(it)
                        },
                        loading = ::showLoading
                    )
            }
        }
    }
}