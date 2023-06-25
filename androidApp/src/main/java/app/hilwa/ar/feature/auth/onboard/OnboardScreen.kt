package app.hilwa.ar.feature.auth.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.hilwa.ar.R
import app.hilwa.ar.components.BottomSheetPrivacyPolicy
import app.hilwa.ar.components.ButtonPrimary
import app.hilwa.ar.components.ButtonSecondary
import app.hilwa.ar.components.TextType
import app.hilwa.ar.components.TextWithAction
import app.hilwa.ar.feature.auth.Authentication
import app.hilwa.ar.feature.auth.signin.SignIn
import app.hilwa.ar.feature.auth.signup.SignUp
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseMainApp
import app.trian.mvi.ui.BaseScreen
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController

object Onboard {
    const val routeName = "Onboard"
}


@Navigation(
    route = Onboard.routeName,
    group = Authentication.routeName,
    viewModel = OnboardViewModel::class
)
@Composable
internal fun OnboardScreen(
    uiContract: UIContract<OnboardState, OnboardIntent, OnboardAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract) {
    val modalBottomSheet =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val privacyPolicyText = listOf(
        TextType.Text(stringResource(id = R.string.text_license_agreement)),
        TextType.Button(stringResource(id = R.string.text_privacy_policy)),
    )

    BaseScreen(
        modalBottomSheetState = modalBottomSheet,
        bottomSheet = {
            BottomSheetPrivacyPolicy(
                onAccept = {
                    launch {
                        modalBottomSheet.hide()
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 20.dp,
                    bottom = 40.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.5f),
                painter = painterResource(id = R.drawable.ic_onboard),
                contentDescription = stringResource(R.string.content_description_image_onboard),
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.title_onboard),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.title1_onboard),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.title2_onboard),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.subtitle_onboard),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonPrimary(
                        text = stringResource(id = R.string.btn_signin),
                        modifier = Modifier
                            .defaultMinSize(
                                minWidth = 150.dp
                            ),
                        fullWidth = false
                    ) {
                        navigator.navigateAndReplace(SignIn.routeName)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ButtonSecondary(
                        text = stringResource(R.string.btn_create_new_account),
                        modifier = Modifier
                            .defaultMinSize(
                                minWidth = 150.dp
                            ),
                        fullWidth = false
                    ) {
                        navigator.navigateSingleTop(SignUp.routeName)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextWithAction(
                    labels = privacyPolicyText,
                    onTextClick = {
                        if (it == 1) {
                            launch {
                                modalBottomSheet.show()
                            }
                        }
                    }
                )

            }
        }
    }
}


@Preview
@Composable
fun PreviewScreenOnboard() {
    BaseMainApp {
        OnboardScreen(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = OnboardState()
            )
        )
    }
}