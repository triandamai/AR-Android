/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.startQuiz

import androidx.lifecycle.SavedStateHandle
import app.hilwa.ar.data.ResultState
import app.hilwa.ar.data.domain.progress.SaveProgressUseCase
import app.hilwa.ar.data.domain.quiz.GetListQuestionUseCase
import app.hilwa.ar.feature.quiz.startQuiz.component.ScreenType
import app.trian.mvi.ui.viewModel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartQuizViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getListQuestionUseCase: GetListQuestionUseCase,
    private val saveProgressUseCase: SaveProgressUseCase
) : MviViewModel<StartQuizState, StartQuizAction>(StartQuizState()) {

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
            .collect {
                when (it) {
                    is ResultState.Error -> commit { copy(screenType = ScreenType.LOADING) }
                    ResultState.Loading -> commit { copy(screenType = ScreenType.LOADING) }
                    is ResultState.Result -> commit {
                        copy(
                            questions = it.data,
                            screenType = if (it.data.isEmpty()) ScreenType.EMPTY else ScreenType.QUIZ
                        )
                    }
                }
            }

    }

    private fun setResponse() = asyncWithState {
        //cannot go next because there's no answer
        if (hasAnswer.isNullOrEmpty()) return@asyncWithState

        val userResponse = Pair(currentQuestionNumber, hasAnswer)

        //we set the answer when there's no existing answer
        val value = if (response.isEmpty()) {
            response.plus(userResponse)
        } else {
            response.toMutableList().apply {
                val index = try {
                    response.withIndex()
                        .first { value -> value.value.first == userResponse.first }
                        .index
                } catch (e: Exception) {
                    (-1)
                }
                if (index != -1) {
                    this[index] = userResponse
                } else {
                    add(userResponse)
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
            .collect {
                when (it) {
                    is ResultState.Error -> commit {
                        copy(
                            visibleButton = true,
                            hasAnswer = null,
                            screenType = ScreenType.LOADING
                        )
                    }

                    ResultState.Loading -> commit {
                        copy(
                            visibleButton = false,
                            hasAnswer = null,
                            screenType = ScreenType.LOADING
                        )
                    }

                    is ResultState.Result -> commit {
                        copy(
                            hasAnswer = null,
                            screenType = ScreenType.RESULT,
                            scoreData = it.data,
                            showResult = true
                        )
                    }
                }
            }

    }

    override fun onAction(action: StartQuizAction) {
        when (action) {
            StartQuizAction.Next -> setResponse()
            StartQuizAction.Prev -> calculatePage(false)
        }
    }

}