/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.quiz

import app.hilwa.ar.data.model.Quiz
import app.trian.mvi.ui.ResultStateData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject

class GetLatestQuizUseCase @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend operator fun invoke(): Flow<ResultStateData<List<Quiz>>> = flow {
        emit(ResultStateData.Loading)
        try {
            val quiz = firestore.collection("QUIZ")
                .whereGreaterThanOrEqualTo("createdAt", LocalDateTime.now().minusDays(2))
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(5)
                .get().await().map { it.toObject(Quiz::class.java).copy(id = it.id) }
            if (quiz.isEmpty()) emit(ResultStateData.Empty)
            else emit(ResultStateData.Result(quiz))
        } catch (e: Exception) {
            emit(ResultStateData.Error(e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}