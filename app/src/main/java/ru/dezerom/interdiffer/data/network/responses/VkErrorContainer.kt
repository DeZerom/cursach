package ru.dezerom.interdiffer.data.network.responses

import ru.dezerom.interdiffer.data.models.VkErrorDataModel

interface VkErrorContainer {
    val vkError: VkErrorDataModel?
}