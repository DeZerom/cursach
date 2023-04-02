package ru.dezerom.interdiffer.domain.models.user

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.utils.PartialDate

@Parcelize
@Immutable
data class VkUserModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val isClosed: Boolean,
    val deactivationType: DeactivationType,
    val birthDate: PartialDate?,
    val photo100: String,
    val photo200: String
): Parcelable {
    val fullName: String
        get() = "$firstName $lastName"
}
