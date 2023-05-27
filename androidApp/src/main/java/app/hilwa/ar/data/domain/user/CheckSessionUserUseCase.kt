package app.hilwa.ar.data.domain.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSessionUserUseCase @Inject constructor() {
    operator fun invoke(): Flow<Boolean> = flow{
        emit(true)
    }
}