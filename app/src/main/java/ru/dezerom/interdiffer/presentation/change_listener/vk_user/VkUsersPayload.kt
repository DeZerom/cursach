package ru.dezerom.interdiffer.presentation.change_listener.vk_user

sealed class VkUsersPayload {

    class UserDeleted(val userId: Int): VkUsersPayload()

}
