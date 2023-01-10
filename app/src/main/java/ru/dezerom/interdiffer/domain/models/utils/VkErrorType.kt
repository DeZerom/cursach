package ru.dezerom.interdiffer.domain.models.utils

import androidx.annotation.StringRes
import ru.dezerom.interdiffer.R

enum class VkErrorType(@StringRes val title: Int, @StringRes val message: Int) {
    UNKNOWN(R.string.unknown_error, R.string.unknown_error_message),
    APP_IS_OFF(R.string.app_is_off, R.string.app_is_off_message),
    REQUESTS_LIMIT(R.string.request_limit, R.string.request_limit_message),
    SAME_TYPE_LIMIT(R.string.same_type, R.string.same_type_message),
    INTERNAL_ERROR(R.string.internal_error, R.string.internal_error_message),
    ACCESS_DENIED(R.string.access_denied_error, R.string.access_denied_error_message),
    PAGE_NOT_FOUND(R.string.page_not_found, R.string.page_not_found_message),
    METHOD_LIMIT(R.string.method_limit, R.string.method_limit_message),
    PRIVATE_PROFILE(R.string.private_profile, R.string.private_profile_message),
    WRONG_IDENTIFIER(R.string.wrong_identifier, R.string.wrong_identifier);

    companion object {
        fun fromCode(code: Int?): VkErrorType = when (code) {
            2 -> APP_IS_OFF
            6 -> REQUESTS_LIMIT
            9 -> SAME_TYPE_LIMIT
            10 -> INTERNAL_ERROR
            15 -> ACCESS_DENIED
            18 -> PAGE_NOT_FOUND
            29 -> METHOD_LIMIT
            30 -> PRIVATE_PROFILE
            113 -> WRONG_IDENTIFIER

            else -> UNKNOWN
        }
    }
}