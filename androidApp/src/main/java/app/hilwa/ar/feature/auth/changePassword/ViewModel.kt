package app.hilwa.ar.feature.auth.changePassword

import app.hilwa.ar.R
import app.hilwa.ar.base.BaseViewModel
import app.hilwa.ar.data.domain.user.ChangePasswordUseCase
import app.hilwa.ar.data.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ChangePasswordState, ChangePasswordEvent>(ChangePasswordState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }


    private fun validateData(
        cb: suspend (String) -> Unit
    ) = asyncWithState {

        hideKeyboard()
        when {
            newPassword != confirmPassword -> showSnackbar(R.string.message_confirm_password_not_match)
            newPassword.isEmpty() || confirmPassword.isEmpty() ->
                showSnackbar(R.string.message_change_password_field_empty)

            else -> cb(newPassword)
        }
    }

    private fun handleResponse(result: ResultState<Boolean>) = async {
        when (result) {
            is ResultState.Error -> hideLoading()
            ResultState.Loading -> showLoading()
            is ResultState.Result -> {
                hideLoading()
                showSnackbar(R.string.text_message_success_change_password)
            }
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            ChangePasswordEvent.Submit -> validateData { newPassword ->
                changePasswordUseCase(newPassword).collect(::handleResponse)
            }
        }
    }

}