package ru.dezerom.interdiffer.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import timber.log.Timber

suspend fun <T> safeDaoCall(
    daoCall: suspend () -> T?
): RequestResult<T> = safeDaoCall(
    daoCall = daoCall,
    onNullValue = { RequestResult.Error.NothingFoundError }
)

suspend fun <T> safeDaoCall(
    daoCall: suspend () -> T?,
    onNullValue: () -> RequestResult<T>
): RequestResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            val result = daoCall()

            if (result == null) {
                onNullValue()
            } else {
                RequestResult.Success(result)
            }
        } catch (e: Exception) {
            Timber.e(e)
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
