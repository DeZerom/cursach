package ru.dezerom.interdiffer.domain.models.comparasion

import androidx.compose.runtime.Immutable
import ru.dezerom.interdiffer.domain.models.society.SocietyCategory
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.domain.models.user.VkUserModel

@Immutable
data class DetailedComparisonModel(
    val comparisonId: Int,
    val firstPerson: VkUserModel,
    val secondPerson: VkUserModel,
    val overallMatching: Double,
    val categoriesMatching: Map<String, Double>,
    val firstPersonCategorizedSocieties: List<SocietyCategory>,
    val secondPersonCategorizedSocieties: List<SocietyCategory>,
    val invalidationReason: ComparisonInvalidationReason?,
    val weakMatches: List<String>, //category name
    val strongMatches: List<VkSocietyModel> //societies
)
