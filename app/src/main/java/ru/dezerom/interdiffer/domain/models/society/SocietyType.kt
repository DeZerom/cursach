package ru.dezerom.interdiffer.domain.models.society

import androidx.annotation.StringRes
import ru.dezerom.interdiffer.R

enum class SocietyType {
    GROUP, PAGE, EVENT, UNKNOWN;

    @StringRes
    fun toStrRes(): Int =
        when (this) {
            GROUP -> R.string.group
            PAGE -> R.string.page
            EVENT -> R.string.event
            UNKNOWN -> R.string.unknown_society
        }

    companion object {
        fun fromString(string: String?) =
            when (string) {
                GROUP_CODE ->
                    GROUP
                PAGE_CODE ->
                    PAGE
                EVENT_CODE ->
                    EVENT
                else ->
                    UNKNOWN
            }

        private const val GROUP_CODE = "group"
        private const val PAGE_CODE = "page"
        private const val EVENT_CODE = "event"
    }
}