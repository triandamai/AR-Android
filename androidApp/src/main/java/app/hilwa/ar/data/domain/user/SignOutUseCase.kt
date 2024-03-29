package app.hilwa.ar.data.domain.user

import android.content.SharedPreferences
import app.hilwa.ar.data.ResultState
import app.hilwa.ar.sqldelight.Database
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val db: Database,
    private val editor:SharedPreferences.Editor,
    private val firebaseAuth: FirebaseAuth
) {
    operator fun invoke(): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)
        try {
            firebaseAuth.signOut()
            emit(ResultState.Result(true))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.orEmpty()))
        }

    }.flowOn(Dispatchers.IO)
}