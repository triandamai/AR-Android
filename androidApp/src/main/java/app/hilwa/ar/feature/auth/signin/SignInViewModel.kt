package app.hilwa.ar.feature.auth.signin

import android.content.Context
import app.hilwa.ar.R
import app.hilwa.ar.data.domain.user.SignInWithEmailAndPasswordUseCase
import app.hilwa.ar.feature.home.Home
import app.trian.mvi.ui.UIEvent
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signInWithEmailUseCase: SignInWithEmailAndPasswordUseCase,
) : MviViewModel<SignInState, SignInIntent, SignInAction>(SignInState()) {

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(
        cb: suspend (String, String) -> Unit
    ) = asyncWithState {
        when {
            email.isEmpty() || password.isEmpty() -> sendUiEvent(
                UIEvent.ShowToast(
                    context.getString(
                        R.string.message_password_or_email_cannot_empty
                    )
                )
            )

            else -> cb(email, password)
        }
    }


    override fun onAction(action: SignInAction) {
        when (action) {
            SignInAction.SignInWithEmail -> validateData { email, password ->
                signInWithEmailUseCase(email, password).onEach(
                    success = {
                        hideLoading()
                        sendUiEvent(
                            UIEvent.ShowToast(
                                context.getString(
                                    R.string.text_message_welcome_user,
                                    String.Empty
                                )
                            )
                        )
                        sendUiEvent(UIEvent.NavigateAndReplace(Home.routeName))
                    },
                    error = { _, stringId ->
                        hideLoading()
                        sendUiEvent(UIEvent.ShowToast(context.getString(stringId)))
                    },
                    loading = ::showLoading
                )
            }
        }
    }

}