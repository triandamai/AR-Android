package app.hilwa.ar.feature.auth.changePassword

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import app.hilwa.ar.R
import app.hilwa.ar.components.AppbarBasic
import app.hilwa.ar.components.BaseMainApp
import app.hilwa.ar.components.BaseScreen
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.components.DialogLoading
import app.hilwa.ar.components.FormInput
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.contract.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController

object ChangePassword {
    const val routeName = "ChangePassword"
}

@Navigation(
    route = ChangePassword.routeName,
    viewModel = ChangePasswordViewModel::class
)
@Composable
internal fun ScreenChangePassword(
    uiContract: UIContract<ChangePasswordState, ChangePasswordAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract) {

    val view = LocalView.current
    val currentWindow = (view.context as? Activity)?.window
    val surface = MaterialTheme.colorScheme.surface.toArgb()
    val dark = isSystemInDarkTheme()

    fun changeStatusBar() {
        (view.context as Activity).window.statusBarColor = surface
        WindowCompat.getInsetsController(currentWindow!!, view).isAppearanceLightStatusBars = !dark
    }

    if (!view.isInEditMode) {
        /* getting the current window by tapping into the Activity */
        SideEffect {
            changeStatusBar()
        }
    }

    DialogLoading(
        show = state.isLoading,
        message = stringResource(R.string.text_message_loading_update_password),
        title = stringResource(R.string.text_title_loading)
    )
    BaseScreen(
        topAppBar = {
            AppbarBasic(
                title = stringResource(id = R.string.title_appbar_change_password),
                onBackPressed = { navigator.navigateUp() })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            FormInput(
                label = {
                    Text(text = stringResource(R.string.label_input_new_password))
                },
                placeholder = stringResource(R.string.placeholder_input_new_password),
                showPasswordObsecure = true,
                initialValue = state.newPassword,
                onChange = {
                    commit { copy(newPassword = it) }
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            FormInput(
                label = {
                    Text(text = stringResource(R.string.label_input_confirm_new_password))
                },
                placeholder = stringResource(R.string.placeholder_input_confirm_new_password),
                showPasswordObsecure = true,
                initialValue = state.confirmPassword,
                onChange = {
                    commit { copy(confirmPassword = it) }
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        controller.keyboard.hide()
                        dispatch(ChangePasswordAction.Submit)
                    }
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            ButtonPrimary(
                text = stringResource(R.string.btn_change_password)
            ) {
                controller.keyboard.hide()
                dispatch(ChangePasswordAction.Submit)
            }
        }
    }
}


@Preview
@Composable
fun PreviewScreenChangePassword() {
    BaseMainApp {
        ScreenChangePassword(
            uiContract = UIContract(
                state = ChangePasswordState(),
                controller = rememberUIController()
            )
        )
    }
}