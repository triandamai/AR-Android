package app.hilwa.ar.feature.auth.signin

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.hilwa.ar.R.string
import app.hilwa.ar.base.listener.AREventListener
import app.hilwa.ar.components.AppbarAuth
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.components.DialogLoading
import app.hilwa.ar.components.FormInput
import app.hilwa.ar.components.TextType
import app.hilwa.ar.components.TextWithAction
import app.hilwa.ar.feature.auth.resetPassword.ResetPassword
import app.hilwa.ar.feature.auth.signup.SignUp
import app.trian.core.ui.BaseMainApp
import app.trian.core.ui.BaseScreen
import app.trian.core.ui.UIListener
import app.trian.core.ui.UIWrapper
import app.trian.core.ui.rememberUIController

object SignIn {
    const val routeName = "SignIn"
}

@Composable
internal fun ScreenSignIn(
    uiEvent: UIListener<SignInState,SignInEvent, AREventListener>
) = UIWrapper(uiEvent) {
    val forgetPasswordTextType = listOf(
        TextType.Text(stringResource(id = string.label_forgot_password)),
        TextType.Button(stringResource(id = string.btn_reset_here))
    )

    val signUp = listOf(
        TextType.Text(stringResource(id = string.label_dont_have_account)),
        TextType.Button(stringResource(id = string.btn_create_new_account))
    )

    DialogLoading(
        show = state.isLoading,
        message = stringResource(string.text_message_loading_signin),
        title = stringResource(string.text_title_loading)
    )
    BaseScreen(
        controller = controller,
        topAppBar = { AppbarAuth(onBackPressed = { router.navigateBackAndClose() }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(string.title_sign_in),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(string.subtitle_signin),
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(36.dp))
                FormInput(
                    label = {
                        Text(
                            text = stringResource(string.label_input_email),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    initialValue = state.email,
                    placeholder = stringResource(string.placeholder_input_email),
                    onChange = { commit { copy(email = it) } },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                FormInput(
                    label = {
                        Text(
                            text = stringResource(string.label_input_password),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    initialValue = state.password,
                    placeholder = stringResource(string.placeholder_input_password),
                    onChange = {
                        commit { copy(password = it) }
                    },
                    showPasswordObsecure = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            dispatch(SignInEvent.SignInWithEmail)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextWithAction(
                    labels = forgetPasswordTextType,
                    onTextClick = {
                        if (it == 1) {
                            router.navigateSingleTop(ResetPassword.routeName)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))
                ButtonPrimary(text = stringResource(id = string.btn_signin)) {
                    dispatch(SignInEvent.SignInWithEmail)
                }

                Spacer(modifier = Modifier.height(30.dp))
                Column {
                    Spacer(modifier = Modifier.height(36.dp))
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextWithAction(
                            labels = signUp,
                            onTextClick = {
                                if (it == 1) {
                                    router.navigateSingleTop(SignUp.routeName)
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}


@Preview
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewScreenSignIn() {
    var state by remember {
        mutableStateOf(SignInState())
    }
    BaseMainApp {
        ScreenSignIn(
            uiEvent = UIListener(
                controller = rememberUIController(
                    event = AREventListener()
                ),
                commit = {
                         state = it
                },
                dispatcher = {},
                state = SignInState()
            )
        )
    }
}