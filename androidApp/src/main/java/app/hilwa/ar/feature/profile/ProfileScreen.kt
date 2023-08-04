/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.hilwa.ar.R
import app.hilwa.ar.components.FormInput
import app.hilwa.ar.feature.auth.changePassword.ChangePassword
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.extensions.getScreenHeight
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.rememberUIController
import app.trian.mvi.ui.theme.ApplicationTheme

object Profile {
    const val routeName = "Profile"
}

@Navigation(
    route = Profile.routeName,
    viewModel = ProfileViewModel::class
)
@Composable
fun ProfileScreen(
    uiContract: UIContract<ProfileState, ProfileEffect, ProfileAction>
) = UIWrapper(uiContract = uiContract) {
    val ctx = LocalContext.current
    val screenHeight = ctx.getScreenHeight()
    val headerHeight = (screenHeight / 3)

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = "Profile"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = 0.dp
            )
        },
        bottomBar = {

        },
        snackbarHost = {},
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_profile),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                headerHeight / 2
                            ),
                        contentScale = ContentScale.FillWidth
                    )
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp,
                                    top = 30.dp
                                )
                                .align(Alignment.TopStart)
                        ) {
                            if (state.isEdit) {
                                FormInput(
                                    initialValue = state.inputDisplayName,
                                    placeholder = "Masukkan nama lengkap",
                                    onChange = {
                                        commit {
                                            copy(
                                                inputDisplayName = it
                                            )
                                        }
                                    }
                                )
                            } else {
                                Text(
                                    text = state.displayName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = state.email,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 16.dp
                                ),
                            horizontalArrangement = Arrangement.End
                        ) {
//                            Button(
//                                onClick = {
//
//                                }
//                            ) {
//                                Text(text = "Edit Profil")
//                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(
                            horizontal = 20.dp
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dummy_avatar_female),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 3.dp,
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            .size(50.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            ListItem(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = {
                        controller.navigator.navigate(ChangePassword.routeName)
                    }
                ),
                text = {
                    Text(
                        text = "Ubah Password",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ApplicationTheme {
        ProfileScreen(
            UIContract(
                rememberUIController(),
                ProfileState()
            )
        )
    }
}