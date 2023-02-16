package ru.dezerom.interdiffer.domain.models.society

enum class ClosedType {
    OPEN, CLOSED, PRIVATE;

    companion object {
        fun fromInt(int: Int?) =
            when (int) {
                0 -> OPEN
                1 -> CLOSED
                else -> PRIVATE
            }
    }
}