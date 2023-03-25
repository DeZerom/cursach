package ru.dezerom.interdiffer.domain.models.enums

import ru.dezerom.interdiffer.R

enum class ComparisonPagerScreens {
    OVERALL_INFO, MATCHES_INFO;

    fun toStrRes() = when (this) {
        OVERALL_INFO -> R.string.overall_info
        MATCHES_INFO -> R.string.matches_info
    }
}
