package ru.dezerom.interdiffer.presentation.utils

import kotlinx.coroutines.flow.MutableSharedFlow

suspend fun <E> MutableSharedFlow<E?>.forceSend(element: E) {
    emit(null)
    emit(element)
}
