package ru.dezerom.interdiffer.mappers

import ru.dezerom.interdiffer.data.models.UserDataModel
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.user.UserModel
import ru.dezerom.interdiffer.domain.utils.toPartialDate

fun UserDataModel.toDomain() =
    UserModel(
        id = id ?: 0,
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        isClosed = isClosed == true,
        deactivationType = DeactivationType.fromString(deactivated),
        birthDate = birthDate.toPartialDate()
    )
