package app.hilwa.ar.feature.auth.signin

import android.content.Context
import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.SignInWithEmailAndPasswordUseCase
import app.hilwa.ar.feature.home.Home
import app.trian.core.ui.ResultState
import app.trian.core.ui.extensions.Empty
import app.trian.core.ui.viewModel.BaseViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val signInWithEmailUseCase: SignInWithEmailAndPasswordUseCase,
) : BaseViewModel<SignInState, SignInEvent>(context, SignInState()) {
    init {
        handleActions()
    }


    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(
        cb: suspend (String, String) -> Unit
    ) = asyncWithState {
        hideKeyboard()
        when {
            email.isEmpty() || password.isEmpty() ->
                snackbar.showSnackbar(R.string.message_password_or_email_cannot_empty)

            else -> cb(email, password)
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            SignInEvent.SignInWithEmail -> validateData { email, password ->
                signInWithEmailUseCase(email, password).onEach(
                    success = {
                        hideLoading()
                        snackbar.showSnackbar(R.string.text_message_welcome_user, String.Empty)
                        navigation.navigateAndReplace(Home.routeName)
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