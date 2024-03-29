/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.historyQuiz

import app.hilwa.ar.data.ResultStateData
import app.hilwa.ar.data.domain.progress.GetListProgressUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getListProgressUseCase: GetListProgressUseCase
) : MviViewModel<HistoryState, HistoryAction>(
    HistoryState()
) {

    init {
        getHistory()
    }

    private fun getHistory() = async {
        getListProgressUseCase()
            .collect{
                when(it){
                    ResultStateData.Empty ->  commit { copy(isLoading = false, isEmpty = true) }
                    is ResultStateData.Error -> commit { copy(isLoading = false, isEmpty = true) }
                    ResultStateData.Loading -> commit { copy(isLoading = true, isEmpty = false) }
                    is ResultStateData.Result -> commit {
                        copy(isLoading = false, isEmpty = false, histories = it.data) }
                }
            }
    }

    override fun onAction(action: HistoryAction) {
        when (action) {
            HistoryAction.GetData -> getHistory()
        }
    }
}