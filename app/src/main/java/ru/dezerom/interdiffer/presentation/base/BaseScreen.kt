package ru.dezerom.interdiffer.presentation.base

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun BaseScreen(viewModel: BaseViewModel, content: @Composable () -> Unit) {
    val sideEffect = viewModel.baseSideEffect.collectAsState(initial = null)
    val state = viewModel.baseScreenState.collectAsState()

    when (val se = sideEffect.value) {
        is BaseSideEffect.ShowToast -> {
            val context = LocalContext.current
            Toast.makeText(context, se.text, Toast.LENGTH_LONG).show()
        }
        null -> {}
    }

    when (state.value) {
        is BaseScreenState.Loading -> LoadingState()
        is BaseScreenState.ShowingError -> ErrorState(state.value)
        is BaseScreenState.ShowingInfo -> content()
    }
}

@Composable
private fun LoadingState() {

}

@Composable
private fun ErrorState(state: BaseScreenState.ShowingError) {

}
