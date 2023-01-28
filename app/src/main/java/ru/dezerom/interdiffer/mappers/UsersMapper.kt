package ru.dezerom.interdiffer.mappers

import ru.dezerom.interdiffer.data.models.VkUserDataModel
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.utils.toPartialDate

fun VkUserDataModel.toDomain() =
    VkUserModel(
        id = id ?: 0,
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        isClosed = isClosed == true,
        deactivationType = DeactivationType.fromString(deactivated),
        birthDate = birthDate.toPartialDate()
    )
