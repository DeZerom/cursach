package ru.dezerom.interdiffer.domain.models.society

import javax.annotation.concurrent.Immutable

@Immutable
data class SocietyCategory(
    val name: String,
    val items: List<VkSocietyModel>
) {
    val count get() = items.size
}