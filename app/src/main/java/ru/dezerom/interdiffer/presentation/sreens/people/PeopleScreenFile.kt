package ru.dezerom.interdiffer.presentation.sreens.people

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dezerom.interdiffer.presentation.items.VkUserItem
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.widgets.*

@Composable
fun PeopleScreen(viewModel: PeopleViewModel) {
    val viewState: PeopleScreenState by viewModel.viewState.collectAsState()

    BaseScreen(viewModel = viewModel) {
        when (viewState) {
            is PeopleScreenState.ShowingList ->
                ShowListState(
                    viewModel = viewModel,
                    viewState = viewState as PeopleScreenState.ShowingList
                )
        }
    }
}

@Composable
private fun ShowListState(
    viewModel: PeopleViewModel,
    viewState: PeopleScreenState.ShowingList
) {
    val items = viewState.list

    if (items.isEmpty()) {
        BaseColumnWidget {
            EmptyListWidget { viewModel.onAddButtonClick() }
        }
    } else {
        BaseLazyColumn {
            items(
                count = items.size,
                key = { items[it].id }
            ) { 
                val item = items[it]

                VkUserItem(
                    model = item,
                    onClick = viewModel::onItemClick,
                    onInfoCircleClick = viewModel::onInfoCircleClick,
                    onDeleteCircleClick = viewModel::onItemDeleteClick
                )
            }
        }
    }
}
