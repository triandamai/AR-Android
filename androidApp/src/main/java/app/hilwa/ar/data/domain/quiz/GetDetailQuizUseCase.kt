/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.quiz

import app.hilwa.ar.R
import app.hilwa.ar.data.ResultState
import app.hilwa.ar.data.model.Quiz
import app.hilwa.ar.data.model.QuizQuestion
import app.hilwa.ar.sqldelight.Database
import app.hilwa.ar.table.question.Question
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetDetailQuizUseCase @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val db: Database
) {
    operator fun invoke(quizId: String): Flow<ResultState<Pair<Quiz, List<QuizQuestion>>>> = flow {
        emit(ResultState.Loading)
        try {
            //get quiz and question from firestore
            val quiz = firebaseFirestore.collection("QUIZ").document(quizId)

            val detailQuiz = quiz.get()
                .await()
                .toObject(Quiz::class.java)

            if (detailQuiz == null) {
                emit(ResultState.Error(stringId = R.string.message_failed_fetch_data))
                return@flow
            }

            val questions = quiz.collection("QUESTION")
                .get()
                .await()
                .map {
                    it.toObject(QuizQuestion::class.java).copy(
                        id = it.id
                    )
                }
            //save quiz and question to local
            db.transaction {
                questions.map {
                    Question(
                        questionId = it.id,
                        quizId = quiz.id,
                        question = it.question,
                        questionImage = it.questionImage,
                        questionNumber = it.questionNumber.toLong(),
                        questionOptions = it.questionOptions.map { answer ->
                            answer.key.plus("#").plus(answer.value)
                        },
                        questionCorrectAnswer = it.questionCorrectAnswer,
                        createdAtSecond = it.createdAt.seconds,
                        createdAtNanoSecond = it.createdAt.nanoseconds.toLong(),
                        updatedAtSecond = it.updatedAt.seconds,
                        updatedAtNanoSecond = it.updatedAt.nanoseconds.toLong(),
                    )
                }.forEach {
                    val exist = db.questionQueries.getExistQuiz(it.questionId).executeAsOneOrNull()
                    if (exist == true) {
                        db.questionQueries.updateQuestion(
                            questionId = it.questionId,
                            quizId = it.quizId,
                            questionNumber = it.questionNumber,
                            questionImage = it.questionImage,
                            question = it.question,
                            questionCorrectAnswer = it.questionCorrectAnswer,
                            questionOptions = it.questionOptions,
                            createdAtSecond = it.createdAtSecond,
                            createdAtNanoSecond = it.createdAtNanoSecond,
                            updatedAtSecond = it.updatedAtSecond,
                            updatedAtNanoSecond = it.updatedAtNanoSecond,
                        )
                    } else {
                        db.questionQueries.insertQuestion(
                            questionId = it.questionId,
                            quizId = it.quizId,
                            questionNumber = it.questionNumber,
                            questionImage = it.questionImage,
                            question = it.question,
                            questionCorrectAnswer = it.questionCorrectAnswer,
                            questionOptions = it.questionOptions,
                            createdAtSecond = it.createdAtSecond,
                            createdAtNanoSecond = it.createdAtNanoSecond,
                            updatedAtSecond = it.updatedAtSecond,
                            updatedAtNanoSecond = it.updatedAtNanoSecond,
                        )
                    }
                }

            }
            emit(ResultState.Result(Pair(detailQuiz, questions)))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}