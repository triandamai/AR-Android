package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignUpWithEmailAndPasswordUseCase @Inject constructor(

) {
    operator fun invoke(
        displayName: String,
        email: String,
        password: String
    ): Flow<Response<Any>> = flow {
        emit(Response.Loading)

        emit(Response.Result("HEHE"))

    }.flowOn(Dispatchers.IO)
}