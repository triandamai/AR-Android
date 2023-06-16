package app.hilwa.ar.feature.auth.resetPassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.R
import app.hilwa.ar.components.AppbarBasic
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.components.DialogLoading
import app.hilwa.ar.components.FormInput
import app.trian.core.annotation.Navigation
import app.trian.core.ui.BaseMainApp
import app.trian.core.ui.BaseScreen
import app.trian.core.ui.UIListener
import app.trian.core.ui.UIWrapper
import app.trian.core.ui.rememberUIController


object ResetPassword {
    const val routeName = "ResetPassword"
}

@Navigation(
    route = ResetPassword.routeName,
    viewModel = ResetPasswordViewModel::class
)
@Composable
internal fun ScreenResetPassword(
    uiEvent: UIListener<ResetPasswordState, ResetPasswordEvent>
) = UIWrapper(uiEvent) {

    DialogLoading(
        show = state.isLoading,
        message = stringResource(R.string.text_message_loading_reset_password),
        title = stringResource(R.string.text_title_loading)
    )

    BaseScreen(
        topAppBar = {
            AppbarBasic(
                title = stringResource(id = R.string.title_appbar_reset_password),
                onBackPressed = {
                    router.navigateUp()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            FormInput(
                label = {
                    Text(
                        text = stringResource(id = R.string.label_input_email),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                placeholder = stringResource(R.string.placeholder_email_reset_password),
                initialValue = state.email,
                onChange = {
                    commit { copy(email = it) }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        dispatch(ResetPasswordEvent.Submit)
                    }
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            ButtonPrimary(
                text = stringResource(R.string.btn_reset_password)
            ) {
                dispatch(ResetPasswordEvent.Submit)
            }
        }
    }
}


@Preview
@Composable
fun PreviewScreenResetPassword() {
    BaseMainApp {
        ScreenResetPassword(
            uiEvent = UIListener(
                controller = rememberUIController(),
                state = ResetPasswordState()
            )
        )
    }
}