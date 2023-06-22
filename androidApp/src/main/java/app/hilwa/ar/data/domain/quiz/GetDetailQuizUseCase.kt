/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.quiz

import app.hilwa.ar.R
import app.hilwa.ar.data.model.Quiz
import app.hilwa.ar.data.model.QuizQuestion
import app.trian.mvi.ui.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetDetailQuizUseCase @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
    operator fun invoke(quizId: String): Flow<ResultState<Pair<Quiz, List<QuizQuestion>>>> = flow {
        emit(ResultState.Loading)
        try {
            val quiz = firebaseFirestore.collection("QUIZ").document(quizId)

            val detailQuiz = quiz.get()
                .await()
                .toObject(Quiz::class.java)
            val questions = quiz.collection("QUESTION")
                .get()
                .await()
                .map {
                    it.toObject(QuizQuestion::class.java).copy(
                        id = it.id
                    )
                }


            if (detailQuiz == null) {
                emit(ResultState.Error(stringId = R.string.message_failed_fetch_data))
                return@flow
            }

            emit(ResultState.Result(Pair(detailQuiz, questions)))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}