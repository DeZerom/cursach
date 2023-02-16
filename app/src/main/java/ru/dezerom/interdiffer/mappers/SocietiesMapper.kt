package ru.dezerom.interdiffer.mappers

import ru.dezerom.interdiffer.data.models.VkSocietyDataModel
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.society.ClosedType
import ru.dezerom.interdiffer.domain.models.society.SocietyAgeLimits
import ru.dezerom.interdiffer.domain.models.society.SocietyType
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel

fun VkSocietyDataModel.toDomain() =
    VkSocietyModel(
        id = id ?: 0,
        name = name ?: "",
        closedType = ClosedType.fromInt(isClosed),
        deactivationType = DeactivationType.fromString(deactivated),
        type = SocietyType.fromString(type),
        photo100 = photo100 ?: "",
        photo200 = photo200 ?: "",
        activity = activity ?: "",
        ageLimits = SocietyAgeLimits.fromCode(ageLimits),
        description = description ?: ""
    )
