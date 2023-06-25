/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import app.hilwa.ar.data.domain.progress.SaveProgressUseCase
import app.hilwa.ar.data.domain.quiz.GetListQuestionUseCase
import app.hilwa.ar.feature.quiz.resultQuiz.ResultQuiz
import app.trian.mvi.ui.UIEvent
import app.trian.mvi.ui.extensions.findIndex
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartQuizViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getListQuestionUseCase: GetListQuestionUseCase,
    private val saveProgressUseCase: SaveProgressUseCase
) : MviViewModel<StartQuizState, StartQuizIntent, StartQuizAction>(
    StartQuizState(),
) {

    private fun quizId(): String = savedStateHandle.get<String>(StartQuiz.argKey).orEmpty()

    init {
        getListQuestion()
    }

    private fun isLastStep() = with(uiState.value) {
        currentIndex == (questions.size - 1)
    }

    private fun calculatePage(isNext: Boolean) = asyncWithState {
        val index = if (isNext) {
            if (!isLastStep()) {
                currentIndex + 1
            } else currentIndex
        } else {
            if (currentIndex > 0) {
                currentIndex - 1
            } else currentIndex
        }
        commit(copy(currentIndex = index, hasAnswer = null))
    }

    private fun getListQuestion() = async {
        getListQuestionUseCase(quizId())
            .onEach(
                loading = {
                    commit { copy(isLoading = true) }
                },
                error = { _, _ ->
                    commit { copy(isLoading = false) }
                },
                success = {
                    commit { copy(questions = it, isLoading = false) }
                }
            )

    }

    private fun setResponse() = asyncWithState {
        //cannot go next because there's no answer
        if (hasAnswer.isNullOrEmpty()) return@asyncWithState

        val res = Pair(currentQuestionNumber, hasAnswer)

        //we set the answer when there's no existing answer
        val value = if (response.isEmpty()) {
            response.plus(res)
        } else {
            response.toMutableList().apply {

                val index = try {
                    response.withIndex()
                        .first { value -> value.value.first == res.first }
                        .index
                } catch (e: Exception) {(-1)}
                if (index != -1) {
                    this[index] = res
                } else {
                    add(res)
                }
            }
        }

        commit {
            copy(response = value)
        }
        if (isLastStep()) {
            saveResponse()
            return@asyncWithState
        }
        calculatePage(true)
    }

    private fun saveResponse() = asyncWithState {
        saveProgressUseCase(quizId = quizId(), response = response)
            .onEach(
                loading = {
                    commit { copy(isLoading = true) }
                },
                error = { _, _ ->
                    commit { copy(isLoading = false) }
                },
                success = {
                    commit { copy(isLoading = false) }
                    sendUiEvent(UIEvent.NavigateAndReplace(ResultQuiz.routeName, quizId()))
                }
            )
    }

    override fun onAction(action: StartQuizAction) {
        when (action) {
            StartQuizAction.Next -> setResponse()
            StartQuizAction.Prev -> calculatePage(false)
        }
    }

}