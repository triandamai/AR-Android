package app.hilwa.ar.data.domain.user

import android.content.SharedPreferences
import app.hilwa.ar.data.utils.Response
import app.hilwa.ar.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val db: Database,
    private val editor:SharedPreferences.Editor
) {
    operator fun invoke(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {

            emit(Response.Result(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message.orEmpty()))
        }

    }.flowOn(Dispatchers.IO)
}