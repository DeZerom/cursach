package ru.dezerom.interdiffer.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dezerom.interdiffer.data.network.responses.VkErrorContainer
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.domain.models.utils.VkErrorType

suspend fun <T: VkErrorContainer> safeVkApiCall(
    call: suspend () -> T?
): RequestResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            val result = call()

            if (result != null) {
                if (result.vkError == null)
                    RequestResult.Success(result)
                else
                    RequestResult.Error.VkError(VkErrorType.fromCode(result.vkError!!.errorCode))
            } else {
                RequestResult.Error.Network
            }
        } catch (e: Exception) {
            RequestResult.Error.Network
        }
    }
}