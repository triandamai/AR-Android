package app.hilwa.ar.feature.auth.resetPassword

import android.content.Context
import android.util.Patterns
import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.ResetPasswordUseCase
import app.trian.core.ui.ResultState
import app.trian.core.ui.extensions.hideKeyboard
import app.trian.core.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    @ApplicationContext context:Context,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseViewModel<ResetPasswordState, ResetPasswordEvent>(context,ResetPasswordState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(cb: suspend (String) -> Unit) = asyncWithState {
        context.hideKeyboard()
        when {
            email.isEmpty() -> snackbar.showSnackbar(R.string.alert_email_empty)
            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> snackbar.showSnackbar(R.string.alert_validation_email)

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
                            snackbar.showSnackbar(R.string.message_success_reset_password)
                            navigation.navigateUp()
                        },
                        error = {
                            hideLoading()
                            snackbar.showSnackbar(it)
                        },
                        loading = ::showLoading
                    )
            }
        }
    }
}