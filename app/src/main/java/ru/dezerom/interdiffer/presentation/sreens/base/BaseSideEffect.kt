package ru.dezerom.interdiffer.presentation.sreens.base

sealed interface BaseSideEffect {
    class ShowToast(val text: String): BaseSideEffect
}