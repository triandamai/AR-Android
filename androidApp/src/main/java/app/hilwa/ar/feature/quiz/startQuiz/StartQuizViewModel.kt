/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz

import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartQuizViewModel @Inject constructor(
) : MviViewModel<StartQuizState, StartQuizIntent, StartQuizAction>(
    StartQuizState(),
) {
    private fun calculatePage(isNext: Boolean) = asyncWithState {
        val index = if (isNext) {
            if (currentIndex < (uiState.value.quiz.size - 1)) {
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


    override fun onAction(action: StartQuizAction) {
        when (action) {
            StartQuizAction.Next -> calculatePage(true)

            StartQuizAction.Prev -> calculatePage(false)
        }
    }

}