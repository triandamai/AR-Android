/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.user

import android.graphics.Bitmap
import app.hilwa.ar.data.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage
) {
    operator fun invoke(
        displayName: String,
        profilePicture: Bitmap?
    ): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)

        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                emit(ResultState.Error("User is not logged in"))
                return@flow
            }

            val profileUrl = if (profilePicture != null) {
                val storageRef =
                    firebaseStorage.reference.child("AR_PROFILE/${currentUser.uid}.jpg")
                val baos = ByteArrayOutputStream()
                profilePicture.compress(
                    Bitmap.CompressFormat.JPEG,
                    80,
                    baos
                )
                val data = baos.toByteArray()
                storageRef.putBytes(data).continueWithTask {
                    if (!it.isSuccessful) {
                        it.exception?.let { throw it }
                    }
                    storageRef.downloadUrl
                }.await()
            } else null

            val requestChangedProfile = userProfileChangeRequest {
                this.displayName = displayName
                if (profileUrl != null) {
                    this.photoUri = profileUrl
                }
            }

            currentUser.updateProfile(
                requestChangedProfile
            ).await()
            emit(ResultState.Result(true))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.orEmpty()))

        }
    }.flowOn(Dispatchers.IO)
}