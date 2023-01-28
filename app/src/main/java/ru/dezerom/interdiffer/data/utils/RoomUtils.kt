package ru.dezerom.interdiffer.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dezerom.interdiffer.domain.models.utils.RequestResult

suspend fun <T> safeDaoCall(
    daoCall: suspend () -> T?
): RequestResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            val result = daoCall()

            if (result == null) {
                RequestResult.Error.RoomError
            } else {
                RequestResult.Success(result)
            }
        } catch (e: Exception) {
            RequestResult.Error.RoomError
        }
    }
}

suspend fun safeDaoAction(
    daoCall: suspend () -> Unit
): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            daoCall()

            true
        } catch (e: Exception) {
            false
        }
    }
}
