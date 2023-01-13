package ru.dezerom.interdiffer.domain.models

enum class DeactivationType {
    ACTIVE, DELETED, BANNED;

    companion object {
        fun fromString(s: String?) =
            when (s) {
                DELETED_CODE ->
                    DELETED
                BANNED_CODE ->
                    BANNED
                else ->
                    ACTIVE
            }

        private const val DELETED_CODE = "deleted"
        private const val BANNED_CODE = "banned"
    }
}