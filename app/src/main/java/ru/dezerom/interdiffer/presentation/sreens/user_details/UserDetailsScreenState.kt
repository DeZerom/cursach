package ru.dezerom.interdiffer.presentation.sreens.user_details

import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.domain.models.user.VkUserModel

sealed interface UserDetailsScreenState {

    object Empty: UserDetailsScreenState

    class ShowDetailsOnly(
        val details: VkUserModel,
        val showSocietiesLoading: Boolean
    ): UserDetailsScreenState

    class ShowDetailsAndSocieties(
        val details: VkUserModel,
        val societies: List<VkSocietyModel>
    ): UserDetailsScreenState

}
