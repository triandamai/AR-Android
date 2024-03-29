package app.hilwa.ar.feature.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.hilwa.ar.R.string
import app.hilwa.ar.components.AppbarAuth
import app.hilwa.ar.components.BaseMainApp
import app.hilwa.ar.components.BaseScreen
import app.hilwa.ar.components.BottomSheetPrivacyPolicy
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.components.CheckBoxWithAction
import app.hilwa.ar.components.DialogLoading
import app.hilwa.ar.components.FormInput
import app.hilwa.ar.components.TextType
import app.hilwa.ar.components.TextWithAction
import app.hilwa.ar.feature.auth.Authentication
import app.hilwa.ar.feature.auth.signin.SignIn
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.contract.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController


object SignUp {
    const val routeName = "SignUp"
}

@Navigation(
    route = SignUp.routeName,
    group=Authentication.routeName,
    viewModel = SignUpViewModel::class
)
@Composable
internal fun ScreenSignUp(
    uiContract: UIContract<SignUpState, SignUpAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract) {
    val modalBottomSheet = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val privacyPolicy = listOf(
        TextType.Text(stringResource(id = string.text_license_agreement)),
        TextType.Button(stringResource(id = string.text_privacy_policy))
    )
    val signInText = listOf(
        TextType.Text(stringResource(id = string.label_already_have_account)),
        TextType.Button(stringResource(id = string.text_signin))
    )



    DialogLoading(
        show = state.isLoading,
        message = stringResource(string.text_message_loading_signup),
        title = stringResource(string.text_title_loading)
    )
    BaseScreen(
        modalBottomSheetState = modalBottomSheet,
        topAppBar = {
            AppbarAuth(
                onBackPressed = {
                    navigator.navigateUp()
                }
            )
        },
        bottomSheet = {
            BottomSheetPrivacyPolicy(
                onAccept = {
                    launch {
                        modalBottomSheet.hide()
                    }

                    commit { copy(agreeTnc = true) }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 20.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(string.title_register),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Row {
                TextWithAction(
                    labels = signInText,
                    onTextClick = {
                        if (it == 1) {
                            controller.navigator.navigateAndReplace(SignIn.routeName)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                FormInput(
                    initialValue = state.displayName,
                    label = {
                        Text(
                            text = stringResource(string.label_input_display_nama),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    placeholder = stringResource(string.placeholder_input_display_name),
                    onChange = {
                        commit { copy(displayName = it) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                FormInput(
                    initialValue = state.email,
                    label = {
                        Text(
                            text = stringResource(id = string.label_input_email),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    placeholder = stringResource(id = string.placeholder_input_email),
                    onChange = {
                        commit { copy(email = it) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                FormInput(
                    initialValue = state.password,
                    label = {
                        Text(
                            text = stringResource(id = string.label_input_password),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    showPasswordObsecure = true,
                    placeholder = stringResource(id = string.placeholder_input_password),
                    onChange = {
                        commit { copy(password = it) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            controller.keyboard.hide()
                            dispatch(SignUpAction.SignUpWithEmail)
                        }
                    )
                )
                FormInput(
                    initialValue = state.confirmPassword,
                    label = {
                        Text(
                            text = stringResource(id = string.label_input_confirm_new_password),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    showPasswordObsecure = true,
                    placeholder = stringResource(id = string.placeholder_input_confirm_new_password),
                    onChange = {
                        commit { copy(confirmPassword = it) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            dispatch(SignUpAction.SignUpWithEmail)
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CheckBoxWithAction(
                    labels = privacyPolicy,
                    checked = state.agreeTnc,
                    onTextClick = {
                        if (it == 1) {
                            launch {
                                modalBottomSheet.show()
                            }
                        }
                    },
                    onCheckedChange = {
                        commit { copy(agreeTnc = it) }
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ButtonPrimary(
                text = stringResource(string.btn_continue),
                enabled = state.agreeTnc

            ) {
                dispatch(SignUpAction.SignUpWithEmail)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Preview
@Composable
fun PreviewScreenSignUp() {
    BaseMainApp {
        ScreenSignUp(
            uiContract = UIContract(
                state = SignUpState(),
                controller = rememberUIController(),
                dispatcher = {},
                mutation = {

                }
            )
        )
    }
}