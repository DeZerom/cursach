package ru.dezerom.interdiffer.presentation.sreens.base

import androidx.annotation.StringRes

sealed interface BaseSideEffect {
    sealed class ShowToast: BaseSideEffect {

        class ByText(val text: String) : ShowToast()

        class ByStringRes(@StringRes val textRes: Int): ShowToast()
    }

    object GoBack: BaseSideEffect
}