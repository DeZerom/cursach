package ru.dezerom.interdiffer.domain.models.society

data class SocietyCategory(
    val name: String,
    val items: List<VkSocietyModel>
) {
    val count get() = items.size
}