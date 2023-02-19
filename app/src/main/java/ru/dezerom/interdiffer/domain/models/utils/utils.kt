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
    onError: (RequestResult.Error) -> Unit
) {
    when (this) {
        is RequestResult.Success -> onSuccess(data)

        is RequestResult.Error -> onError(this)
    }
}
