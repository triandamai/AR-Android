/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.listQuiz

import app.hilwa.ar.data.domain.quiz.GetListQuizUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListQuizViewModel @Inject constructor(
    private val getListQuizUseCase: GetListQuizUseCase
) : MviViewModel<ListQuizState, ListQuizIntent, ListQuizAction>(
    ListQuizState()
) {
    init {
        getListQuiz()
    }

    private fun getListQuiz() = async {
        getListQuizUseCase()
            .onEach(
                loading = {
                    commit { copy(isLoading = true) }
                },
                error = {_,_->
                    commit { copy(isLoading = false) }
                },
                success = {
                    commit{copy(isLoading=false,quiz = it)}
                },
                empty = {
                    commit{copy(isLoading=false)}
                }
            )
    }


    override fun onAction(action: ListQuizAction) {
        //no empty
    }

}