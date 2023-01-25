package ru.dezerom.interdiffer.presentation.sreens.people

import androidx.compose.runtime.*
import ru.dezerom.interdiffer.presentation.dialogs.AddVkUserDialog
import ru.dezerom.interdiffer.presentation.dialogs.InfoCirclesDescriptionDialogScreen
import ru.dezerom.interdiffer.presentation.items.VkUserItem
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.widgets.BaseColumnWidget
import ru.dezerom.interdiffer.presentation.widgets.BaseLazyColumn
import ru.dezerom.interdiffer.presentation.widgets.EmptyListWidget

@Composable
fun PeopleScreen(viewModel: PeopleViewModel) {
    val viewState: PeopleScreenState by viewModel.viewState.collectAsState()
    val sideEffect = viewModel.sideEffect.collectAsState(initial = null)

    when (sideEffect.value) {
        is PeopleScreenSideEffect.ShowInfoCirclesDescription -> {
            val showState = remember { mutableStateOf(true) }
            showState.value = true

            InfoCirclesDescriptionDialogScreen(showState)
        }

        is PeopleScreenSideEffect.ShowAddUserDialog -> {
            val showState = remember { mutableStateOf(true) }
            showState.value = true

            AddVkUserDialog(
                showState = showState,
                onUserAdd = { viewModel.onUserAddButtonClick(it) }
            )
        }

        null -> {}
    }

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
