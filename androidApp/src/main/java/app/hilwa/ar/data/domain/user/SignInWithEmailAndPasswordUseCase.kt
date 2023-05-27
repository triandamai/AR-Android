package app.hilwa.ar.data.domain.user;

import app.hilwa.ar.data.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(

) {
    operator fun invoke(email: String, password: String): Flow<Response<Any>> = flow {
        emit(Response.Loading)

        emit(Response.Result("HEHE"))
    }.flowOn(Dispatchers.IO)
}
