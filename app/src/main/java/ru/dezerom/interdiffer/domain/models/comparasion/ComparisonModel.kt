package ru.dezerom.interdiffer.domain.models.comparasion

import ru.dezerom.interdiffer.domain.models.user.VkUserModel

data class ComparisonModel(
    val id: Int,
    val firstPerson: VkUserModel,
    val secondPerson: VkUserModel
)
