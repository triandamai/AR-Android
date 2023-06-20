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
import app.trian.mvi.ui.viewModel.MviViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailQuizViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getDetailQuizUseCase: GetDetailQuizUseCase
) : MviViewModelData<DetailQuizState, DetailQuizDataState, DetailQuizEvent>(
    DetailQuizState(),
    DetailQuizDataState()
) {
    init {
        handleActions()
        getQuiz()
    }

    private fun quizId(): String = savedStateHandle.get<String>(DetailQuiz.argKey).orEmpty()

    private fun getQuiz() = async {
        commit {
            copy(
                quizId=quizId()
            )
        }
//        getDetailQuizUseCase(quizId())
//            .onEach(
//                loading = {},
//                error = {},
//                success = {
//                    commitData { copy(quiz = it) }
//                }
//            )
    }

    override fun handleActions() = onEvent {}

}