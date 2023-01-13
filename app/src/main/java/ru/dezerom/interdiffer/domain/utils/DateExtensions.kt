package ru.dezerom.interdiffer.domain.utils

import ru.dezerom.interdiffer.domain.models.utils.PartialDate

fun String?.toPartialDate(): PartialDate? {
    if (this == null) return null

    val parts = split(".")

    val day = parts.getOrNull(0)?.toIntOrNull() ?: return null
    val month = parts.getOrNull(1)?.toIntOrNull() ?: return null
    val year = parts.getOrNull(2)?.toIntOrNull()

    return PartialDate(day, month, year)
}

fun PartialDate.toDayMonthString(): String {
    val dayString = if (day < 10)
        "0$day"
    else
        day.toString()

    val monthString = if (month < 10)
        "0$month"
    else
        month.toString()

    return "$dayString.$monthString"
}

fun PartialDate.toDayMonthYearString(): String {
    val dayMonthString = toDayMonthString()

    return if (year != null)
        "$dayMonthString.$year"
    else
        dayMonthString
}
