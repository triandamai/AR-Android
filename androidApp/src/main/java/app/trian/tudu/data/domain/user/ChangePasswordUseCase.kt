package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(

) {
    operator fun invoke(newPassword: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)

    }.flowOn(Dispatchers.IO)
}