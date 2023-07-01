/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.progress

import app.hilwa.ar.data.model.ProgressModel
import app.hilwa.ar.sqldelight.Database
import app.trian.mvi.ui.ResultState
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SaveProgressUseCase @Inject constructor(
    private val db: Database,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    operator fun invoke(
        quizId: String,
        response: List<Pair<Int, String>>
    ): Flow<ResultState<ProgressModel>> = flow {
        emit(ResultState.Loading)

        try {
            //capture current time
            val timeStamp = Timestamp.now()

            //load question to calculate score from user response
            val question = db
                .questionQueries
                .getQuestionByQuiz(quizId)
                .executeAsList()


            //get amount the right answer
            val score = question
                .map { q ->
                    response
                        .firstOrNull { it.first.toLong() == q.questionNumber }
                        .let {
                            q.questionCorrectAnswer == it?.second
                        }
                }
                .count { it }

            //calculate the score
            val finalScore = if (score < 1) 0
            else (score / question.size) * 100

            //prepare data for uploading
            val map = hashMapOf<String, String>()
            response.map { res ->
                map[res.first.toString()] = res.second
            }
            val progress = ProgressModel(
                quizId = quizId,
                quizScore = score,
                questionResponse = map,
                amountRightAnswer = finalScore,
                amountQuestion = question.size,
                createdAt = timeStamp,
                updateAt = timeStamp
            )

            //upload to firestore
            firestore
                .collection("USER")
                .document(auth.currentUser?.uid.orEmpty())
                .collection("PROGRESS")
                .document(quizId)
                .set(progress, SetOptions.merge())
                .await()

            db.transaction {
                //save to local db update when data already exist
                val exist = db.progressQueries.getExist(quizId).executeAsOneOrNull()
                if (exist == true) {
                    db.progressQueries.updateProgress(
                        quizId = progress.quizId,
                        questionResponse = progress.questionResponse.map {
                            it.key.plus("#").plus(it.value)
                        },
                        quizScore = progress.quizScore.toLong(),
                        createdAtSecond = timeStamp.seconds,
                        createdAtNanoSecond = timeStamp.nanoseconds.toLong(),
                        updatedAtSecond = timeStamp.seconds,
                        updatedAtNanoSecond = timeStamp.nanoseconds.toLong()
                    )
                } else {
                    db.progressQueries.insertProgress(
                        quizId = progress.quizId,
                        questionResponse = progress.questionResponse.map {
                            it.key.plus("#").plus(it.value)
                        },
                        quizScore = progress.quizScore.toLong(),
                        createdAtSecond = timeStamp.seconds,
                        createdAtNanoSecond = timeStamp.nanoseconds.toLong(),
                        updatedAtSecond = timeStamp.seconds,
                        updatedAtNanoSecond = timeStamp.nanoseconds.toLong()
                    )
                }
            }
            emit(ResultState.Result(progress))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}