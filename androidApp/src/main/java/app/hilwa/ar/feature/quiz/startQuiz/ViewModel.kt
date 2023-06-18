/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz

import android.content.Context
import app.trian.mvi.ui.viewModel.BaseViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class StartQuizViewModel @Inject constructor(
    @ApplicationContext context:Context
) : BaseViewModelData<StartQuizState, StartQuizDataState, StartQuizEvent>(
    context,
    StartQuizState(),
    StartQuizDataState()
) {
    init {
        handleActions()
    }

    private fun calculatePage(isNext: Boolean) = asyncWithState {
        val index = if (isNext) {
            if (currentIndex < (uiDataState.value.quiz.size - 1)) {
                currentIndex + 1
            } else currentIndex
        } else {
            if (currentIndex > 0) {
                currentIndex - 1
            } else currentIndex
        }
        commit(
            copy(
                currentIndex = index,
                hasAnswer = null
            )
        )
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            StartQuizEvent.Next -> calculatePage(true)

            StartQuizEvent.Prev -> calculatePage(false)
        }
    }

}