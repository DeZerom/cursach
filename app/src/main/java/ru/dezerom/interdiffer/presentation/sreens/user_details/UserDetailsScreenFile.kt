package ru.dezerom.interdiffer.presentation.sreens.user_details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel) {
    BaseScreen(viewModel = viewModel) {
        Text(text = "Details")
    }
}
