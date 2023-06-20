package app.hilwa.ar.feature.auth.changePassword

import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.ChangePasswordUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : MviViewModel<ChangePasswordState, ChangePasswordEvent>(ChangePasswordState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }


    private fun validateData(
        cb: suspend (String) -> Unit
    ) = asyncWithState {

        controller.keyboard.hide()
        when {
            newPassword != confirmPassword ->
                controller.snackBar.show(R.string.message_confirm_password_not_match)

            newPassword.isEmpty() || confirmPassword.isEmpty() ->
                controller.snackBar.show(R.string.message_change_password_field_empty)

            else -> cb(newPassword)
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            ChangePasswordEvent.Submit -> validateData { newPassword ->
                changePasswordUseCase(newPassword).onEach(
                    success = {
                        hideLoading()
                        controller.snackBar.show(R.string.text_message_success_change_password)
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