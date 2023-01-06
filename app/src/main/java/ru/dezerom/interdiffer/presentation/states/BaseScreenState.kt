package ru.dezerom.interdiffer.presentation.states

sealed class BaseScreenState {
    object ShowingContent: BaseScreenState()
    object Loading: BaseScreenState()
}
