/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.quiz

import app.hilwa.ar.data.model.Quiz
import app.hilwa.ar.sqldelight.Database
import app.trian.core.ui.ResultStateData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetQuizUseCase @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val db: Database
) {
    suspend operator fun invoke(): Flow<ResultStateData<List<Quiz>>> = flow {
        emit(ResultStateData.Loading)
        try {
            val quiz = firebaseFirestore
                .collection("")
                .get()
                .await()
                .documents
                .map { it.toObject(Quiz::class.java)!! }
                .sortedByDescending { it.quizQuestionAmount }

            emit(ResultStateData.Result(quiz))
        } catch (e: Exception) {
            emit(ResultStateData.Error(e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}