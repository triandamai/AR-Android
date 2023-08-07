package app.hilwa.ar.feature.auth.signin

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import app.hilwa.ar.R
import app.hilwa.ar.data.ResultState
import app.hilwa.ar.data.domain.user.SignInWithEmailAndPasswordUseCase
import app.hilwa.ar.feature.home.Home
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signInWithEmailUseCase: SignInWithEmailAndPasswordUseCase,
) : MviViewModel<SignInState, SignInAction>(SignInState()) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(
        cb: suspend (String, String) -> Unit
    ) = asyncWithState {
        when {
            email.isEmpty() || password.isEmpty() -> {
                showToast(
                    context.getString(
                        R.string.message_password_or_email_cannot_empty
                    ), Toast.LENGTH_LONG
                )
            }

            else -> cb(email, password)
        }
    }


    override fun onAction(action: SignInAction) {
        when (action) {
            SignInAction.SignInWithEmail -> validateData { email, password ->
                signInWithEmailUseCase(email, password).collect {
                    when (it) {
                        is ResultState.Error -> {
                            hideLoading()
                            showToast(context.getString(it.stringId), Toast.LENGTH_LONG)
                        }

                        ResultState.Loading -> showLoading()
                        is ResultState.Result -> {
                            hideLoading()
                            showToast(
                                context.getString(
                                    R.string.text_message_welcome_user,
                                    String.Empty
                                ),
                                Toast.LENGTH_LONG
                            )
                            navigateAndReplace(Home.routeName)
                        }
                    }
                }
            }
        }
    }

}