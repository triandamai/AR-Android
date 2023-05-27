package app.hilwa.ar.feature.auth.resetPassword

import android.util.Patterns
import app.hilwa.ar.R
import app.hilwa.ar.base.BaseViewModel
import app.hilwa.ar.data.domain.user.ChangePasswordUseCase
import app.hilwa.ar.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ResetPasswordState, ResetPasswordEvent>(ResetPasswordState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(cb: suspend (String) -> Unit) = asyncWithState {
        when {
            email.isEmpty() -> showSnackbar(R.string.alert_email_empty)
            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> showSnackbar(R.string.alert_validation_email)

            else -> cb(email)
        }
    }

    private fun handleResponse(result: Response<Boolean>) {
        when (result) {
            Response.Loading -> showLoading()
            is Response.Error -> {
                hideLoading()
                showSnackbar(result.message)
            }
            is Response.Result -> {
                hideLoading()
                showSnackbar(R.string.message_success_reset_password)
                navigateUp()
            }
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            ResetPasswordEvent.Submit -> validateData { email ->
                resetPasswordUseCase(email).collect(::handleResponse)
            }
        }
    }
}