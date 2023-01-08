package ru.dezerom.interdiffer.presentation.base

import androidx.annotation.StringRes
import ru.dezerom.interdiffer.R

sealed interface BaseScreenState {

    object ShowingInfo: BaseScreenState

    object Loading: BaseScreenState

    class ShowingError(@StringRes val title: Int, @StringRes val message: Int): BaseScreenState

    companion object {
        val ShowUnknownError = ShowingError(R.string.unknown_error, R.string.unknown_error_message)
    }
}