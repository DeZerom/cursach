package ru.dezerom.interdiffer.presentation.utils

import kotlinx.coroutines.channels.Channel

suspend fun <E> Channel<E?>.forceSend(element: E) {
    send(null)
    send(element)
}
