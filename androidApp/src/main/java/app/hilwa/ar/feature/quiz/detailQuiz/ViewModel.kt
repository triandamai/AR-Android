/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.detailQuiz

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import app.hilwa.ar.data.domain.quiz.GetDetailQuizUseCase
import app.trian.core.ui.viewModel.BaseViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DetailQuizViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val getDetailQuizUseCase: GetDetailQuizUseCase
) : BaseViewModelData<DetailQuizState, DetailQuizDataState, DetailQuizEvent>(
    context,
    DetailQuizState(),
    DetailQuizDataState()
) {
    init {
        handleActions()
        getQuiz()
    }

    private val quizId: String by lazy {
        savedStateHandle.get<String>(DetailQuiz.routeName).orEmpty()
    }

    private fun getQuiz() = async {
        getDetailQuizUseCase(quizId)
            .onEach(
                loading = {},
                error = {},
                success = {
                    commitData { copy(quiz = it) }
                }
            )
    }

    override fun handleActions() = onEvent {}

}