/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.progress

import app.hilwa.ar.data.model.ProgressModel
import app.hilwa.ar.data.model.Quiz
import app.trian.mvi.ui.ResultStateData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetListProgressUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    operator fun invoke(): Flow<ResultStateData<List<Pair<Quiz, ProgressModel>>>> = flow {
        emit(ResultStateData.Loading)

        try {
            val user = auth.currentUser!!

            val progress = firestore.collection("USER")
                .document(user.uid.orEmpty())
                .collection("PROGRESS")
                .get()
                .await()
                .map {
                    it.toObject(ProgressModel::class.java)
                }
                .map {
                    val quiz = firestore.collection("QUIZ")
                        .document(it.quizId)
                        .get().await().toObject(Quiz::class.java)!!
                    Pair(quiz, it)
                }
            if (progress.isEmpty()) emit(ResultStateData.Empty)
            else
                emit(ResultStateData.Result(progress))

        } catch (e: Exception) {
            emit(ResultStateData.Error(e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}