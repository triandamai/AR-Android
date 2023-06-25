/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.domain.user

import app.trian.mvi.ui.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetUserProfileUseCase @Inject constructor(
    private val auth: FirebaseAuth
) {
    operator fun invoke(): Flow<ResultState<FirebaseUser>> = flow {
        emit(ResultState.Loading)
        val user = auth.currentUser!!
        emit(ResultState.Result(user))
    }.flowOn(Dispatchers.IO)
}