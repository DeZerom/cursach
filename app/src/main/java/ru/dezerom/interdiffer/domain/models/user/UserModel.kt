package ru.dezerom.interdiffer.domain.models.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.utils.PartialDate

@Parcelize
data class UserModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val isClosed: Boolean,
    val deactivationType: DeactivationType,
    val birthDate: PartialDate?
): Parcelable {
    val fullName: String
        get() = "$firstName $lastName"
}
