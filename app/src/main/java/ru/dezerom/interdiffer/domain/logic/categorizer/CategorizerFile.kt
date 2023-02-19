package ru.dezerom.interdiffer.domain.logic.categorizer

import ru.dezerom.interdiffer.domain.models.society.SocietyCategory
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel

fun categorizeSocieties(societies: List<VkSocietyModel>): List<SocietyCategory> {
    val sorted = societies.sortedBy { it.activity }

    var activity = sorted.firstOrNull()?.activity
        ?: return listOf(SocietyCategory("", emptyList()))

    var buffer = mutableListOf<VkSocietyModel>()
    val result = mutableListOf<SocietyCategory>()

    sorted.forEach {
        if (it.activity != activity) {
            result.add(SocietyCategory(name = activity, items = buffer))

            activity = it.activity
            buffer = mutableListOf()
        }

        buffer.add(it)
    }

    return result
}

fun List<SocietyCategory>.countOfSocieties(): Int =
    this.sumOf { it.count }
