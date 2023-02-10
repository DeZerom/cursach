package ru.dezerom.interdiffer.presentation.sreens.people

sealed interface PeopleScreenSideEffect {
    object ShowInfoCirclesDescription: PeopleScreenSideEffect
    object ShowAddUserDialog: PeopleScreenSideEffect

    class NavigateToUserDetails(val userId: Int): PeopleScreenSideEffect
}
