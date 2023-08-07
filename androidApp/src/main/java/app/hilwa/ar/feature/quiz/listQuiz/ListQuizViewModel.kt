/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.listQuiz

import app.hilwa.ar.data.ResultStateData
import app.hilwa.ar.data.domain.quiz.GetListQuizUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListQuizViewModel @Inject constructor(
    private val getListQuizUseCase: GetListQuizUseCase
) : MviViewModel<ListQuizState, ListQuizAction>(
    ListQuizState()
) {
    init {
        getListQuiz()
    }

    private fun getListQuiz() = async {
        getListQuizUseCase()
            .collect{
                when(it){
                    ResultStateData.Empty -> commit { copy(isLoading = false, isEmpty = true) }
                    is ResultStateData.Error ->  commit { copy(isLoading = false, isEmpty = true) }
                    ResultStateData.Loading ->  commit { copy(isLoading = true, isEmpty = false) }
                    is ResultStateData.Result ->  commit { copy(isLoading = false, isEmpty = false, quiz = it.data) }
                }
            }
    }


    override fun onAction(action: ListQuizAction) {
        //no empty
    }

}