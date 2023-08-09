/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.profile

import android.widget.Toast
import app.hilwa.ar.data.ResultState
import app.hilwa.ar.data.domain.user.GetUserProfileUseCase
import app.hilwa.ar.data.domain.user.UpdateProfileUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : MviViewModel<ProfileState, ProfileAction>(
    ProfileState()
) {

    init {
        getProfileUser()
    }

    private fun getProfileUser() = async {
        getUserProfileUseCase().collect {
            when (it) {
                is ResultState.Error -> Unit
                ResultState.Loading -> Unit
                is ResultState.Result -> {
                    commit {
                        copy(
                            email = it.data.email.orEmpty(),
                            displayName = it.data.displayName.orEmpty(),
                            inputEmail = it.data.email.orEmpty(),
                            inputDisplayName = it.data.displayName.orEmpty(),
                            profilePicture = it.data.photoUrl.toString()
                        )
                    }
                }
            }
        }
    }

    private fun updateProfile() = asyncWithState {
        updateProfileUseCase(
            inputDisplayName,
            bitmap
        ).collect {
            when (it) {
                is ResultState.Error -> {
                    commit { copy(loading=false) }
                    showToast(it.message, Toast.LENGTH_SHORT)
                }

                ResultState.Loading -> {
                    commit { copy(loading=true) }
                }
                is ResultState.Result -> {
                    commit {
                        copy(
                            isEdit = !isEdit,
                            displayName = inputDisplayName,
                            loading=false
                        )
                    }
                    showToast("Berhasil merubah profile", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    override fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.SubmitUpdateProfile -> updateProfile()
        }
    }
}