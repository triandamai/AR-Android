/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.listQuiz

import android.content.Context
import app.trian.core.ui.viewModel.BaseViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ListQuizViewModel @Inject constructor(
    @ApplicationContext context:Context
) : BaseViewModelData<ListQuizState, ListQuizDataState, ListQuizEvent>(
    context,
    ListQuizState(),
    ListQuizDataState()
) {
    init {
        handleActions()
    }

    override fun handleActions() = onEvent {}

}