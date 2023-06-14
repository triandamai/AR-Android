package app.hilwa.ar.feature.auth.changePassword

import android.content.Context
import app.hilwa.ar.data.domain.user.ChangePasswordUseCase
import app.hilwa.ar.data.utils.ResultState
import app.trian.core.ui.extensions.hideKeyboard
import app.trian.core.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    @ApplicationContext context:Context,
    private val changePasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ChangePasswordState, ChangePasswordEvent>(context,ChangePasswordState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }


    private fun validateData(
        cb: suspend (String) -> Unit
    ) = asyncWithState {

        context.hideKeyboard()
        when {
            newPassword != confirmPassword -> {}//showSnackbar(R.string.message_confirm_password_not_match)
            newPassword.isEmpty() || confirmPassword.isEmpty() ->{}
               // showSnackbar(R.string.message_change_password_field_empty)

            else -> cb(newPassword)
        }
    }

    private fun handleResponse(result: ResultState<Boolean>) = async {
        when (result) {
            is ResultState.Error -> hideLoading()
            ResultState.Loading -> showLoading()
            is ResultState.Result -> {
                hideLoading()
                //showSnackbar(R.string.text_message_success_change_password)
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