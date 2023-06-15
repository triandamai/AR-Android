/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz.listQuiz

import android.content.Context
import app.hilwa.ar.data.domain.quiz.GetListQuizUseCase
import app.trian.core.ui.viewModel.BaseViewModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListQuizViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getListQuizUseCase: GetListQuizUseCase
) : BaseViewModelData<ListQuizState, ListQuizDataState, ListQuizEvent>(
    context,
    ListQuizState(),
    ListQuizDataState()
) {
    init {
        handleActions()
        getListQuiz()
    }

    private fun getListQuiz() = async {
        getListQuizUseCase()
            .onEach(
                loading = {},
                error = {},
                success = {},
                empty = {}
            )
    }

    override fun handleActions() = onEvent {}

}