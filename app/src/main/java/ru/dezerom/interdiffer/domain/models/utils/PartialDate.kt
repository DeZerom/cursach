package ru.dezerom.interdiffer.domain.models.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.dezerom.interdiffer.domain.utils.toDayMonthYearString

@Parcelize
data class PartialDate(
    val day: Int,
    val month: Int,
    val year: Int?
): Parcelable {
    val hasYear: Boolean get() = year != null

    override fun toString(): String =
        toDayMonthYearString()

}
