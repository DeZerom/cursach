package ru.dezerom.interdiffer.domain.models.society

enum class SocietyAgeLimits {
    NONE, SIXTEEN, EIGHTEEN;

    companion object {
        fun fromCode(code: Int?) =
            when (code) {
                SIXTEEN_CODE ->
                    SIXTEEN
                EIGHTEEN_CODE ->
                    EIGHTEEN
                else ->
                    NONE
            }

        private const val SIXTEEN_CODE = 2
        private const val EIGHTEEN_CODE = 3
    }
}