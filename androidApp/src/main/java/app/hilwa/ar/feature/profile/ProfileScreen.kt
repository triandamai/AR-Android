/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.profile

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import app.hilwa.ar.R
import app.hilwa.ar.components.FormInput
import app.hilwa.ar.feature.auth.changePassword.ChangePassword
import app.hilwa.ar.ui.ApplicationTheme
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.extensions.getBitmap
import app.trian.mvi.ui.extensions.getScreenHeight
import app.trian.mvi.ui.internal.contract.UIContract
import app.trian.mvi.ui.internal.rememberUIController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale

object Profile {
    const val routeName = "Profile"
}

@Navigation(
    route = Profile.routeName,
    viewModel = ProfileViewModel::class
)
@Composable
fun ProfileScreen(
    uiContract: UIContract<ProfileState, ProfileAction>
) = UIWrapper(uiContract = uiContract) {
    val ctx = LocalContext.current
    val screenHeight = ctx.getScreenHeight()
    val headerHeight = (screenHeight / 3)
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


    val image = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(ctx)
            .data(state.displayName)
            .placeholder(R.drawable.dummy_avatar_female)
            .error(R.drawable.dummy_avatar_female)
            .scale(Scale.FILL)
            .build()
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let {
                val bitmap = it.getBitmap(ctx.contentResolver)
                commit {
                    copy(
                        bitmap = bitmap
                    )
                }
            }
        }
    )

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        color = MaterialTheme.colorScheme.onSurface
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
                                    top = 50.dp
                                )
                                .align(Alignment.TopStart)
                        ) {
                            if (state.isEdit) {
                                Spacer(modifier = Modifier.height(16.dp))
                                FormInput(
                                    initialValue = state.inputDisplayName,
                                    placeholder = "Masukkan nama lengkap",
                                    label = {
                                        Text(text = "Name Lengkap")
                                    },
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
                            Button(
                                onClick = {
                                    commit {
                                        copy(
                                            isEdit = !state.isEdit
                                        )
                                    }
                                }
                            ) {
                                Text(
                                    text = if (state.isEdit) "Save Changes" else "Edit Profil",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
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
                    Box(
                        modifier = Modifier.size(80.dp)
                    ) {
                        if (state.bitmap != null) {
                            Image(
                                bitmap = state.bitmap!!.asImageBitmap(),
                                contentDescription = "",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = 3.dp,
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    .size(80.dp),
                                contentScale = ContentScale.FillWidth
                            )
                        } else {
                            Image(
                                painter = image,
                                contentDescription = "",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = 3.dp,
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    .size(80.dp),
                                contentScale = ContentScale.FillWidth
                            )
                        }
                        if (state.isEdit) {
                            Column(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        imagePicker.launch("image/**")
                                    }
                                    .background(Color.Gray.copy(alpha = 0.4f)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AddBox,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
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