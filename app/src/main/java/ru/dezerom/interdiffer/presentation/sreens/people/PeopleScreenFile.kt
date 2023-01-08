package ru.dezerom.interdiffer.presentation.sreens.people

import androidx.compose.runtime.Composable
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.widgets.EmptyListWidget

@Composable
fun PeopleScreen(viewModel: PeopleViewModel) {
    BaseScreen(viewModel = viewModel) {
        EmptyListWidget { viewModel.onAddButtonClick() }
    }
}
