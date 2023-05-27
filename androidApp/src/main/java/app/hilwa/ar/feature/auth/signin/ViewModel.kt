package app.hilwa.ar.feature.auth.signin

import app.hilwa.ar.R
import app.hilwa.ar.base.BaseViewModel
import app.hilwa.ar.base.extensions.Empty
import app.hilwa.ar.data.domain.user.SignInWithEmailAndPasswordUseCase
import app.hilwa.ar.data.utils.Response
import app.hilwa.ar.feature.home.Home
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailAndPasswordUseCase,
) : BaseViewModel<SignInState, SignInEvent>(SignInState()) {
    init {
        handleActions()
    }


    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(
        cb: suspend (String, String) -> Unit
    ) = asyncWithState {
        when {
            email.isEmpty() || password.isEmpty() ->
                showSnackbar(R.string.message_password_or_email_cannot_empty)

            else -> cb(email, password)
        }
    }

    private fun handleResponse(result: Response<Any>) {
        when (result) {
            Response.Loading -> showLoading()
            is Response.Error -> {
                hideLoading()
                result.showErrorSnackbar()
            }

            is Response.Result -> {
                hideLoading()
                showSnackbar(R.string.text_message_welcome_user, String.Empty)
                navigateAndReplaceAll(Home.routeName)
            }
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            SignInEvent.SignInWithEmail -> validateData { email, password ->
                signInWithEmailUseCase(email, password).collect(::handleResponse)
            }
        }
    }

}