package ru.dezerom.interdiffer.domain.models.utils

sealed class RequestResult<out T> {

    class Success<out T>(val data: T): RequestResult<T>()

    sealed class Error: RequestResult<Nothing>() {
        object Network: Error()

        class VkError(val type: VkErrorType): Error()
    }
}