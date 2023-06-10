package app.hilwa.ar.feature.auth.changePassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.hilwa.ar.UIController
import app.hilwa.ar.R
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.BaseScreen
import app.hilwa.ar.base.UIWrapper
import app.hilwa.ar.base.UIWrapperListener
import app.hilwa.ar.base.pageWrapper
import app.hilwa.ar.components.AppbarBasic
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.components.DialogLoading
import app.hilwa.ar.components.FormInput
import app.hilwa.ar.rememberUIController

object ChangePassword {
    const val routeName = "ChangePassword"
}

@Composable
internal fun ScreenChangePassword(
    state: ChangePasswordState = ChangePasswordState(),
    invoker: UIWrapperListener<ChangePasswordState, ChangePasswordEvent>
) = UIWrapper(invoker = invoker) {

    DialogLoading(
        show = state.isLoading,
        message = stringResource(R.string.text_message_loading_update_password),
        title = stringResource(R.string.text_title_loading)
    )
    BaseScreen(
        topAppBar = {
            AppbarBasic(
                title = stringResource(id = R.string.title_appbar_change_password),
                onBackPressed = { navigateUp() })
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
                        dispatch(ChangePasswordEvent.Submit)
                    }
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            ButtonPrimary(
                text = stringResource(R.string.btn_change_password)
            ) {
                dispatch(ChangePasswordEvent.Submit)
            }
        }
    }
}


@Preview
@Composable
fun PreviewScreenChangePassword() {
    BaseMainApp {
        ScreenChangePassword(
            state = ChangePasswordState(),
            invoker = UIWrapperListener(
                state = ChangePasswordState(),
                controller = rememberUIController()
            )
        )
    }
}