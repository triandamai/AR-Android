/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home


import app.hilwa.ar.data.ResultState
import app.hilwa.ar.data.ResultStateData
import app.hilwa.ar.data.domain.quiz.GetLatestQuizUseCase
import app.hilwa.ar.data.domain.user.GetUserProfileUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestQuizUseCase: GetLatestQuizUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : MviViewModel<HomeState, HomeAction>(HomeState()) {
    init {
        getCurrentUserProfile()
    }

    private fun getCurrentUserProfile() = async {
        getUserProfileUseCase()
            .collect{
                when(it){
                    is ResultState.Error -> Unit
                    ResultState.Loading -> Unit
                    is ResultState.Result -> commit {
                        copy(
                            fullName = it.data.displayName.orEmpty(),
                            profilePicture = it.data.photoUrl.toString()
                        )
                    }
                }
            }
    }

    private fun getLatestQuiz() = async {
//        getLatestQuizUseCase()
//            .collect{
//                when(it){
//                    ResultStateData.Empty ->  commit { copy(isLoadingLatestQuiz = false) }
//                    is ResultStateData.Error ->  commit { copy(isLoadingLatestQuiz = false) }
//                    ResultStateData.Loading ->  commit { copy(isLoadingLatestQuiz = true) }
//                    is ResultStateData.Result ->  commit { copy(latestQuiz = it.data, isLoadingLatestQuiz = false) }
//                }
//            }
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
