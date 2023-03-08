package ru.dezerom.interdiffer.presentation.sreens.user_picker

import ru.dezerom.interdiffer.domain.models.user.VkUserModel

sealed interface UserPickerScreenState {

    object Empty: UserPickerScreenState

    class PickFirst(val users: List<VkUserModel>) : UserPickerScreenState

    class PickSecond(val users: List<VkUserModel>) : UserPickerScreenState

}
