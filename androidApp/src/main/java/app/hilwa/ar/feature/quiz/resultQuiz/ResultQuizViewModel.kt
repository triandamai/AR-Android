/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.resultQuiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import app.hilwa.ar.data.domain.progress.GetProgressUseCase
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultQuizViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProgressUseCase: GetProgressUseCase
) : MviViewModel<ResultQuizState, ResultQuizIntent, ResultQuizAction>(
    ResultQuizState()
) {

    init {
        getProgress()
    }

    private fun quizId() = savedStateHandle.get<String>(ResultQuiz.argKey).orEmpty()

    private fun getProgress() = async {
        getProgressUseCase(quizId())
            .onEach(
                loading = {},
                error = { _, _ -> },
                success = {
                    Log.e("HEHE",it.toString())
                }
            )
    }

    override fun onAction(action: ResultQuizAction) {
        TODO("Not yet implemented")
    }
}