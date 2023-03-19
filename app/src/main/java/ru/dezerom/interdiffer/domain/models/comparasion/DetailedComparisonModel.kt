package ru.dezerom.interdiffer.domain.models.comparasion

import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.domain.models.user.VkUserModel

data class DetailedComparisonModel(
    val comparisonId: Int,
    val firstPerson: VkUserModel,
    val secondPerson: VkUserModel,
    val overallMatching: Double,
    val categoriesMatching: Map<String, Double>,
    val firstPersonSocieties: List<VkSocietyModel>,
    val secondPersonSocieties: List<VkSocietyModel>,
    val invalidationReason: ComparisonInvalidationReason?,
    val weakMatches: List<String>, //category name
    val strongMatches: List<VkSocietyModel> //societies
)
