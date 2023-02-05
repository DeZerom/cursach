package ru.dezerom.interdiffer.presentation.utils.res.destinations

sealed class NestedNavDestinations(
    route: String
) {

    object VkUserDetails: NestedNavDestinations("vk_user_details")

}
