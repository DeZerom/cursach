package ru.dezerom.interdiffer.domain.logic.categorizer

import ru.dezerom.interdiffer.domain.models.society.SocietyCategory
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel

fun categorizeSocieties(societies: List<VkSocietyModel>): List<SocietyCategory> {
    val map = mutableMapOf<String, MutableList<VkSocietyModel>>()

    societies.forEach {
        val entry = map[it.activity]

        if (entry == null) {
            map[it.activity] = mutableListOf(it)
        } else {
            entry.add(it)
        }
    }

    return map.map {
        SocietyCategory(it.key, it.value)
    }
}

fun List<SocietyCategory>.countOfSocieties(): Int =
    this.sumOf { it.count }
