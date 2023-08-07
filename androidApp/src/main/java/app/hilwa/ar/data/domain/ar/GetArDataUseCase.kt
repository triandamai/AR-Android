/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.ar

import app.hilwa.ar.data.ResultStateData
import app.hilwa.ar.data.model.ItemAR
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetArDataUseCase @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    operator fun invoke(arId: String): Flow<ResultStateData<ItemAR>> = flow {
        emit(ResultStateData.Loading)
        try {
            val data = firestore.collection("ITEM_AR")
                .document(arId)
                .get()
                .await().toObject(ItemAR::class.java)!!

            emit(ResultStateData.Result(data))

        } catch (e: Exception) {
            emit(ResultStateData.Error(e.message.orEmpty()))
        }
    }
}