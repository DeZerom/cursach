package ru.dezerom.interdiffer.presentation.sreens.people

import ru.dezerom.interdiffer.domain.models.user.VkUserModel

sealed interface PeopleScreenState {

    class ShowingList(val list: List<VkUserModel>): PeopleScreenState

}
