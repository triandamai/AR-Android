/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.profile

import app.hilwa.ar.data.domain.user.GetUserProfileUseCase
import app.hilwa.ar.data.domain.user.UpdateProfileUseCase
import app.trian.mvi.ui.ResultState
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : MviViewModel<ProfileState, ProfileEffect, ProfileAction>(
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
                            inputDisplayName = it.data.displayName.orEmpty()
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
                is ResultState.Error -> Unit
                ResultState.Loading -> Unit
                is ResultState.Result -> {

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