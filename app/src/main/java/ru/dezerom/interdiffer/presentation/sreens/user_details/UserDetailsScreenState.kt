package ru.dezerom.interdiffer.presentation.sreens.user_details

import ru.dezerom.interdiffer.domain.models.society.SocietyCategory
import ru.dezerom.interdiffer.domain.models.user.VkUserModel

sealed class UserDetailsScreenState {

    abstract val userName: String

    object Empty: UserDetailsScreenState() {
        override val userName: String
            get() = ""
    }

    class ShowDetailsOnly(
        val details: VkUserModel,
        val showSocietiesLoading: Boolean
    ): UserDetailsScreenState() {
        override val userName: String
            get() = details.fullName
    }

    class ShowDetailsAndSocieties(
        val details: VkUserModel,
        val categories: List<SocietyCategory>
    ): UserDetailsScreenState() {
        override val userName: String
            get() = details.fullName
    }

}
