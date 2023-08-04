/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home


import app.hilwa.ar.data.domain.quiz.GetLatestQuizUseCase
import app.hilwa.ar.data.domain.user.GetUserProfileUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestQuizUseCase: GetLatestQuizUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : MviViewModel<HomeState, HomeIntent, HomeAction>(HomeState()) {
    init {
        getCurrentUserProfile()
    }

    private fun getCurrentUserProfile() = async {
        getUserProfileUseCase()
            .onEach(
                success = {
                    commit {
                        copy(
                            fullName = it.displayName.orEmpty(),
                            profilePicture = it.photoUrl.toString()
                        )
                    }
                }
            )
    }

    private fun getLatestQuiz() = async {
        getLatestQuizUseCase()
            .onEach(
                loading = {
                    commit { copy(isLoadingLatestQuiz = true) }
                },
                error = { _, _ ->
                    commit { copy(isLoadingLatestQuiz = false) }
                },
                success = {
                    commit { copy(latestQuiz = it, isLoadingLatestQuiz = false) }
                },
                empty = {
                    commit { copy(isLoadingLatestQuiz = false) }
                }
            )
    }

    override fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.GetLatestData -> {
                getCurrentUserProfile()
                getLatestQuiz()
            }
        }
    }


}
