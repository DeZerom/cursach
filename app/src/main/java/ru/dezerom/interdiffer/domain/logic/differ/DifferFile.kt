package ru.dezerom.interdiffer.domain.logic.differ

import ru.dezerom.interdiffer.domain.logic.categorizer.categorizeSocieties
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonInvalidationReason
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.comparasion.DetailedComparisonModel
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import java.lang.Integer.min

fun createDetailedComparison(
    comparison: ComparisonModel,
    firstPersonSocieties: List<VkSocietyModel>,
    secondPersonSocieties: List<VkSocietyModel>
): DetailedComparisonModel {
    return DetailedComparisonModel(
        comparisonId = comparison.id,
        firstPerson = comparison.firstPerson,
        secondPerson = comparison.secondPerson,
        firstPersonSocieties = firstPersonSocieties,
        secondPersonSocieties = secondPersonSocieties,
        overallMatching = computeOverallMatching(firstPersonSocieties, secondPersonSocieties),
        categoriesMatching = categoriesMatching(firstPersonSocieties, secondPersonSocieties),
        invalidationReason = validateComparison(
            comparison.firstPerson,
            comparison.secondPerson,
            firstPersonSocieties,
            secondPersonSocieties
        ),
        weakMatches = findWeakMatches(firstPersonSocieties, secondPersonSocieties),
        strongMatches = findStrongMatches(firstPersonSocieties, secondPersonSocieties)
    )
}

private fun findWeakMatches(
    listA: List<VkSocietyModel>,
    listB: List<VkSocietyModel>
): List<String> {
    val topA = categorizeSocieties(listA.subList(0, min(listA.size, TOP_SOCIETIES_COUNT)))
    val topB = categorizeSocieties(listB.subList(0, min(listB.size, TOP_SOCIETIES_COUNT)))

    return topA.mapNotNull { categoryA ->
        if (topB.any { it.name == categoryA.name })
            categoryA.name
        else
            null
    }
}

private fun findStrongMatches(
    listA: List<VkSocietyModel>,
    listB: List<VkSocietyModel>
): List<VkSocietyModel> {
    val topB = listB.subList(0, min(listB.size, TOP_SOCIETIES_COUNT))

    return listA.subList(0, min(listA.size, TOP_SOCIETIES_COUNT)).filter { societyA ->
        topB.any { it.id == societyA.id }
    }
}

private fun validateComparison(
    firstUser: VkUserModel,
    secondUser: VkUserModel,
    listA: List<VkSocietyModel>,
    listB: List<VkSocietyModel>
): ComparisonInvalidationReason? {
    return if (
        listA.size < TOP_SOCIETIES_COUNT
        && listB.size < TOP_SOCIETIES_COUNT)
    {
        ComparisonInvalidationReason.TooFewSocieties(firstUser, secondUser)
    } else if (listA.size < TOP_SOCIETIES_COUNT) {
        ComparisonInvalidationReason.TooFewSocieties(firstUser)
    } else if (listB.size < TOP_SOCIETIES_COUNT) {
        ComparisonInvalidationReason.TooFewSocieties(secondUser)
    } else {
        null
    }
}

private fun categoriesMatching(
    listA: List<VkSocietyModel>,
    listB: List<VkSocietyModel>
): Map<String, Double> {
    val categoriesA = categorizeSocieties(listA)
    val categoriesB = categorizeSocieties(listB)

    val seenCategoriesNames = mutableListOf<String>()

    val result = mutableMapOf<String, Double>()
    categoriesA.forEach { categoryA ->
        val categoryB = categoriesB.find { categoryA.name == it.name }

        result[categoryA.name] = if (categoryB != null)
            computeOverallMatching(categoryA.items, categoryB.items)
        else
            0.0

        seenCategoriesNames.add(categoryA.name)
    }

    categoriesB.filter { !seenCategoriesNames.contains(it.name) }.forEach {
        result[it.name] = 0.0
    }

    return result
}

private fun computeOverallMatching(
    listA: List<VkSocietyModel>,
    listB: List<VkSocietyModel>
): Double {
    val inCommon = listA.filter { societyA ->
        listB.any { societyB -> societyA.id == societyB.id }
    }.size.toDouble()

    return inCommon / (listA.size + listB.size)
}

private const val TOP_SOCIETIES_COUNT = 5
