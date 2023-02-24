package ru.dezerom.interdiffer.presentation.sreens.user_details

import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel

sealed interface UserDetailsSideEffect {

    object ShowInfoCirclesDialog: UserDetailsSideEffect

    class ShowSocietyDetailsDialog(
        val society: VkSocietyModel
    ) : UserDetailsSideEffect

}
