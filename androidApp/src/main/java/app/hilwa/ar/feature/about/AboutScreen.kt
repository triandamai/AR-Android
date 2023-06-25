/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.about

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.hilwa.ar.R
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseScreen
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController
import app.trian.mvi.ui.theme.ApplicationTheme
import kotlinx.coroutines.delay

object About {
    const val routeName = "About"
}

@Navigation(
    route = About.routeName,
    viewModel = AboutViewModel::class
)
@Composable
fun AboutScreen(
    uiContract: UIContract<AboutState, AboutIntent, AboutAction>,
    event: BaseEventListener
) = UIWrapper(uiContract = uiContract) {
    LaunchedEffect(key1 = Unit, block = {
        delay(400)
        commit { copy(showContent = true) }
    })
    BaseScreen(
        topAppBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "")
                    }
                },
            )
        }
    ) {
        AnimatedVisibility(
            visible = state.showContent,
            enter = slideInVertically(
                initialOffsetY = {
                    it / 2
                },
            ),
            exit = slideOutVertically(
                targetOffsetY = {
                    it / 2
                },
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 20.dp
                    ),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.title_onboard),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.title1_onboard),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "v 1.0",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Augmented Reality Sistem Saraf Otak dibuat dengan Kotlin dan Jetpack compose oleh Hilwah Mauludiah NIM 12001008 untuk memenuhi syarat tugas akhir Penulisan Ilmiah.",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Semua assets dari model,ilustrasi didapatkan dari sumber penggunaan non komersial.",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal
                    )
                }
                Column {
                    Text(
                        text = "Dipersembahkan Oleh",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = "Hilwah Mauludiah - 12001008")

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Dibuat Dengan",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo_android),
                            contentDescription = "",
                            modifier = Modifier.height(height = 20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo_kotlin),
                            contentDescription = "",
                            modifier = Modifier.height(height = 20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo_sceneform),
                            contentDescription = "",
                            modifier = Modifier.height(height = 20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAboutScreen() {
    ApplicationTheme {
        AboutScreen(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = AboutState(
                    showContent = true
                )
            ),
            event = EventListener()
        )
    }
}