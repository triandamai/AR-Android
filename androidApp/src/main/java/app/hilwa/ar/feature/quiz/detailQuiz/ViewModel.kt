/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.detailQuiz

import app.hilwa.ar.base.BaseViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailQuizViewModel @Inject constructor(
) : BaseViewModelData<DetailQuizState, DetailQuizDataState, DetailQuizEvent>(
    DetailQuizState(),
    DetailQuizDataState()
) {
    init {
        handleActions()
    }

    override fun handleActions() = onEvent {}

}