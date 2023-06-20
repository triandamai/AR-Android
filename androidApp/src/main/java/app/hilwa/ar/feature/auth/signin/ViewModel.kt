package app.hilwa.ar.feature.auth.signin

import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.SignInWithEmailAndPasswordUseCase
import app.hilwa.ar.feature.home.Home
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailAndPasswordUseCase,
) : MviViewModel<SignInState, SignInEvent>(SignInState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(
        cb: suspend (String, String) -> Unit
    ) = asyncWithState {
        controller.keyboard.hide()
        when {
            email.isEmpty() || password.isEmpty() ->
                controller.snackBar.show(R.string.message_password_or_email_cannot_empty)

            else -> cb(email, password)
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            SignInEvent.SignInWithEmail -> validateData { email, password ->
                signInWithEmailUseCase(email, password).onEach(
                    success = {
                        hideLoading()
                        controller.snackBar.show(R.string.text_message_welcome_user, String.Empty)
                        controller.navigator.navigateAndReplace(Home.routeName)
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