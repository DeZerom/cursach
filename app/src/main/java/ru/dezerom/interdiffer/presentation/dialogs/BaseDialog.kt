package ru.dezerom.interdiffer.presentation.dialogs

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Dialog

@Composable
fun BaseDialogScreen(
    showState: MutableState<Boolean>,
    content: @Composable () -> Unit
) {

    BackHandler(enabled = showState.value) {
        showState.value = false
    }

    if (showState.value) {
        Dialog(onDismissRequest = { showState.value = false }) {
            content()
        }
    }
}
