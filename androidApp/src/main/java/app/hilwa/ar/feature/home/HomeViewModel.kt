/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.home


import app.hilwa.ar.data.domain.quiz.GetLatestQuizUseCase
import app.trian.mvi.ui.UIEvent
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestQuizUseCase:GetLatestQuizUseCase
) : MviViewModel<HomeState, HomeIntent, HomeAction>(HomeState()) {

    private fun getLatestQuiz()=async {
        getLatestQuizUseCase()
            .onEach(
                loading = {},
                error={_,_->},
                success = {
                    commit{copy(latestQuiz = it)}
                },
                empty = {
                    sendUiEvent(UIEvent.ShowToast("No latest quiz found"))
                }
            )
    }

    override fun onAction(action: HomeAction) {
        when(action){
            HomeAction.GetLatestData -> getLatestQuiz()
        }
    }


}
