package ru.dezerom.interdiffer.domain.models.comparasion

data class ComparisonCommon(
    val overallMatching: Double,
    val categoriesMatching: List<Pair<String, Double>>,

)
