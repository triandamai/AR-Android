package app.hilwa.ar.feature.auth.resetPassword

import android.content.Context
import android.util.Patterns
import app.hilwa.ar.data.domain.user.ChangePasswordUseCase
import app.hilwa.ar.data.utils.ResultState
import app.trian.core.ui.extensions.hideKeyboard
import app.trian.core.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    @ApplicationContext context:Context,
    private val resetPasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ResetPasswordState, ResetPasswordEvent>(context,ResetPasswordState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(cb: suspend (String) -> Unit) = asyncWithState {
        context.hideKeyboard()
        when {
            email.isEmpty() -> {}//showSnackbar(R.string.alert_email_empty)
            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> {}//showSnackbar(R.string.alert_validation_email)

            else -> cb(email)
        }
    }

    private fun handleResponse(result: ResultState<Boolean>) {
        when (result) {
            ResultState.Loading -> showLoading()
            is ResultState.Error -> {
                hideLoading()
                //showSnackbar(result.message)
            }
            is ResultState.Result -> {
                hideLoading()
               // showSnackbar(R.string.message_success_reset_password)
                navigation.navigateUp()
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