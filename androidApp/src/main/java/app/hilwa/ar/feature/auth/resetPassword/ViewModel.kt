package app.hilwa.ar.feature.auth.resetPassword

import android.util.Patterns
import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.ResetPasswordUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : MviViewModel<ResetPasswordState, ResetPasswordEvent>(ResetPasswordState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(cb: suspend (String) -> Unit) = asyncWithState {
        controller.keyboard.hide()
        when {
            email.isEmpty() -> controller.snackBar.show(R.string.alert_email_empty)
            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> controller.snackBar.show(R.string.alert_validation_email)

            else -> cb(email)
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            ResetPasswordEvent.Submit -> validateData { email ->
                resetPasswordUseCase(email)
                    .onEach(
                        success = {
                            hideLoading()
                            controller.snackBar.show(R.string.message_success_reset_password)
                            controller.navigator.navigateUp()
                        },
                        error = {
                            hideLoading()
                            controller.snackBar.show(it)
                        },
                        loading = ::showLoading
                    )
            }
        }
    }
}