package app.hilwa.ar.feature.auth.signin

import app.hilwa.ar.data.domain.user.SignInWithEmailAndPasswordUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailAndPasswordUseCase,
) : MviViewModel<SignInState,SignInIntent, SignInAction>(SignInState()) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(
        cb: suspend (String, String) -> Unit
    ) = asyncWithState {
      //  controller.keyboard.hide()
        when {
            email.isEmpty() || password.isEmpty() ->{}
               // controller.snackBar.show(R.string.message_password_or_email_cannot_empty)

            else -> cb(email, password)
        }
    }


    override fun onAction(action: SignInAction) {
        when (action) {
            SignInAction.SignInWithEmail -> validateData { email, password ->
                signInWithEmailUseCase(email, password).onEach(
                    success = {
                        hideLoading()
                     //   controller.snackBar.show(R.string.text_message_welcome_user, String.Empty)
                       // controller.navigator.navigateAndReplace(Home.routeName)
                    },
                    error = {_,_->
                        hideLoading()
                     //   controller.snackBar.show(it)
                    },
                    loading = ::showLoading
                )
            }
        }
    }

}