package ru.dezerom.interdiffer.mappers

import ru.dezerom.interdiffer.data.models.SocietyDataModel
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.society.SocietyAgeLimits
import ru.dezerom.interdiffer.domain.models.society.SocietyModel
import ru.dezerom.interdiffer.domain.models.society.SocietyType

fun SocietyDataModel.toDomain() =
    SocietyModel(
        id = id ?: 0,
        name = name ?: "",
        isClosed = isClosed == true,
        deactivationType = DeactivationType.fromString(deactivated),
        type = SocietyType.fromString(type),
        photo100 = photo100 ?: "",
        photo200 = photo200 ?: "",
        activity = activity ?: "",
        ageLimits = SocietyAgeLimits.fromCode(ageLimits),
        description = description ?: ""
    )
