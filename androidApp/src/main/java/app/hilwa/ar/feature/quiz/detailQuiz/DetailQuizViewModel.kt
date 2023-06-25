/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.detailQuiz

import androidx.lifecycle.SavedStateHandle
import app.hilwa.ar.data.domain.quiz.GetDetailQuizUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailQuizViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getDetailQuizUseCase: GetDetailQuizUseCase
) : MviViewModel<DetailQuizState, DetailQuizIntent, DetailQuizAction>(
    DetailQuizState()
) {
    init {
        getQuiz()
    }

    private fun quizId(): String = savedStateHandle.get<String>(DetailQuiz.argKey).orEmpty()

    private fun getQuiz() = async {
        commit { copy(quizId = quizId()) }
        getDetailQuizUseCase(quizId())
            .onEach(
                loading = { commit { copy(isLoading = true) } },
                error = { _, _ -> commit { copy(isLoading = false) } },
                success = { commit { copy(quiz = it.first, isLoading = false) } }
            )
    }

    override fun onAction(action: DetailQuizAction) {
        //no empty
    }

}