package ru.dezerom.interdiffer.domain.models.utils

fun <T> RequestResult<T>.handle(
    onSuccess: (T) -> Unit,
    onError: (RequestResult.Error) -> Unit
) {
    when (this) {
        is RequestResult.Success -> onSuccess(data)

        is RequestResult.Error -> onError(this)
    }
}

suspend fun <T> RequestResult<T>.suspendHandle(
    onSuccess: suspend (T) -> Unit,
    onError: suspend (RequestResult.Error) -> Unit
) {
    when (this) {
        is RequestResult.Success -> onSuccess(data)

        is RequestResult.Error -> onError(this)
    }
}

fun <T, R> RequestResult<T>.castError(): RequestResult<R> {
    return when (this) {
        is RequestResult.Success -> RequestResult.Error.Network

        is RequestResult.Error.VkError -> this

        RequestResult.Error.Network -> RequestResult.Error.Network
        RequestResult.Error.NothingFoundError -> RequestResult.Error.NothingFoundError
        RequestResult.Error.RoomError -> RequestResult.Error.RoomError
    }
}
