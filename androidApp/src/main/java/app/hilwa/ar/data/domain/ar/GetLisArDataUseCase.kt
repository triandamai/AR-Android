/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.ar

import app.hilwa.ar.data.model.ItemAR
import app.trian.mvi.ui.ResultStateData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetLisArDataUseCase @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    operator fun invoke(): Flow<ResultStateData<List<ItemAR>>> = flow {
        emit(ResultStateData.Loading)
        try {
            val data = firestore.collection("ITEM_AR")
                .get()
                .await()
                .documents.map {
                    it.toObject(ItemAR::class.java)!!
                }
            if (data.isEmpty()) {
                emit(ResultStateData.Empty)
            } else {
                emit(ResultStateData.Result(data))
            }
        } catch (e: Exception) {
            emit(ResultStateData.Error(e.message.orEmpty()))
        }
    }
}