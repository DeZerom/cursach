package ru.dezerom.interdiffer.domain.models.utils

import ru.dezerom.interdiffer.R

sealed class RequestResult<out T> {

    class Success<out T>(val data: T): RequestResult<T>()

    sealed class Error: RequestResult<Nothing>() {
        object Network: Error()

        object RoomError: Error()
        class VkError(val type: VkErrorType): Error(), HandleableError {
            override val title: Int
                get() = type.title
            override val message: Int
                get() = type.message
        }

        object NothingFoundError: Error(), HandleableError {
            override val title: Int = R.string.nothing_found_error_title
            override val message: Int = R.string.nothing_found_error_message
        }
    }
}