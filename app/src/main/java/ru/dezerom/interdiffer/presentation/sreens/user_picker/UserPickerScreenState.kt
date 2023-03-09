package ru.dezerom.interdiffer.presentation.sreens.user_picker

import ru.dezerom.interdiffer.domain.models.user.VkUserModel

sealed interface UserPickerScreenState {

    object Empty: UserPickerScreenState

    data class PickingUsers(
        val users: List<VkUserModel>,
        val isPickingFirst: Boolean,
        val firstPickedUser: VkUserModel? = null,
        val secondPickedUser: VkUserModel? = null,
        val isAddingAllowed: Boolean = false
    ): UserPickerScreenState

}
