/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.quiz

import app.hilwa.ar.data.model.QuizQuestion
import app.hilwa.ar.sqldelight.Database
import app.trian.mvi.ui.ResultState
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetListQuestionUseCase @Inject constructor(
    private val db: Database
) {
    operator fun invoke(quizId: String): Flow<ResultState<List<QuizQuestion>>> = flow {
        emit(ResultState.Loading)

        val question = db.questionQueries.getQuestionByQuiz(quizId)
            .executeAsList()
            .map {
                val options = hashMapOf<String, String>()
                it.questionOptions.map {
                    val split = it.split("#")
                    options[split[0]] = split[1]
                }
                QuizQuestion(
                    id = it.questionId,
                    quizId = it.quizId,
                    question = it.question,
                    questionImage = it.questionImage,
                    questionOptions = options,
                    questionCorrectAnswer = it.questionCorrectAnswer,
                    questionNumber = it.questionNumber.toInt(),
                    createdAt = Timestamp.now(),
                    updatedAt = Timestamp.now()
                )
            }

        emit(ResultState.Result(question))

    }.flowOn(Dispatchers.IO)
}