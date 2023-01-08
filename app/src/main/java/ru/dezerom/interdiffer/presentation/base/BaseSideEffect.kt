package ru.dezerom.interdiffer.presentation.base

sealed interface BaseSideEffect {
    class ShowToast(val text: String): BaseSideEffect
}