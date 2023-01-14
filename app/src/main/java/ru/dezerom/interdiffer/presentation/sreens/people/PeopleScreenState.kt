package ru.dezerom.interdiffer.presentation.sreens.people

import ru.dezerom.interdiffer.domain.models.user.UserModel

sealed interface PeopleScreenState {

    class ShowingList(val list: List<UserModel>): PeopleScreenState

}
