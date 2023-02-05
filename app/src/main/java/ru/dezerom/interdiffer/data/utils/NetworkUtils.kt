package ru.dezerom.interdiffer.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dezerom.interdiffer.data.network.responses.VkErrorContainer
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.domain.models.utils.VkErrorType

suspend fun <T: VkErrorContainer, R> safeVkApiCall(
    call: suspend () -> T?,
    successMapper: (T) -> R?,
    onNullValue: () -> RequestResult<R> = { RequestResult.Error.Network }
): RequestResult<R> {
    return withContext(Dispatchers.IO) {
        try {
            val callResult = call()

            if (callResult != null) {
                if (callResult.vkError == null) {
                    val result = successMapper(callResult)

                    if (result != null)
                        RequestResult.Success(result)
                    else
                        onNullValue()
                } else {
                    RequestResult.Error.VkError(VkErrorType.fromCode(callResult.vkError?.errorCode))
                }
            } else {
                RequestResult.Error.Network
            }
        } catch (e: Exception) {
            RequestResult.Error.Network
        }
    }
}