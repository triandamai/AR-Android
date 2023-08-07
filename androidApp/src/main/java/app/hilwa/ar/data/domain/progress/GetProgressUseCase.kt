/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.progress

import app.hilwa.ar.data.ResultState
import app.hilwa.ar.data.model.ProgressModel
import app.hilwa.ar.sqldelight.Database
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetProgressUseCase @Inject constructor(
    private val db: Database
) {
    operator fun invoke(quizId: String): Flow<ResultState<ProgressModel>> = flow {
        emit(ResultState.Loading)
        val result = db
            .progressQueries
            .getProgressByQuiz(quizId)
            .executeAsOneOrNull()

        if (result == null) {
            emit(ResultState.Error("Cannot find result"))
            return@flow
        }
        val response = hashMapOf<String, String>()
        result.questionResponse.forEach {
            val split = it.split("#")
            response[split[0]] = split[1]
        }
        val progressModel = ProgressModel(
            quizId = result.quizId,
            questionResponse = response,
            quizScore = result.quizScore.toInt(),
            createdAt = Timestamp(result.createdAtSecond, result.createdAtNanoSecond.toInt()),
            updateAt = Timestamp(result.updatedAtSecond, result.updatedAtNanoSecond.toInt())
        )
        emit(ResultState.Result(progressModel))
    }.flowOn(Dispatchers.IO)
}