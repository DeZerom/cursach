package ru.dezerom.interdiffer.domain.models.society

enum class SocietyType {
    GROUP, PAGE, EVENT, UNKNOWN;

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