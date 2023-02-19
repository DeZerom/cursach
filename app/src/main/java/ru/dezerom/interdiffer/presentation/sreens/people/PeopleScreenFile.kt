package ru.dezerom.interdiffer.presentation.sreens.people

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.presentation.dialogs.AddVkUserDialog
import ru.dezerom.interdiffer.presentation.dialogs.InfoCirclesDescriptionDialogScreen
import ru.dezerom.interdiffer.presentation.items.VkUserItem
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.utils.MaxSizeModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.utils.res.destinations.NestedNavDestinations
import ru.dezerom.interdiffer.presentation.widgets.BaseColumnWidget
import ru.dezerom.interdiffer.presentation.widgets.BaseLazyColumn
import ru.dezerom.interdiffer.presentation.widgets.EmptyListWidget
import ru.dezerom.interdiffer.ui.theme.Orange

@Composable
fun PeopleScreen(
    viewModel: PeopleViewModel,
    navController: NavController
) {
    val viewState: PeopleScreenState by viewModel.viewState.collectAsState()
    val sideEffect = viewModel.sideEffect.collectAsState(initial = null)

    when (val se = sideEffect.value) {
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
                onUserAdd = viewModel::onUserAddButtonClick
            )
        }

        is PeopleScreenSideEffect.NavigateToUserDetails -> {
            LaunchedEffect(key1 = Unit) {
                navController.navigate(NestedNavDestinations.VkUserDetails.withArg(se.userId))
            }
        }

        null -> {}
    }

    BaseScreen(viewModel = viewModel, navController = navController) {
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
        Box(modifier = MaxSizeModifier) {
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

            FloatingActionButton(
                onClick = viewModel::onAddButtonClick,
                backgroundColor = Orange,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = Dimens.Paddings.smallPadding,
                        bottom = Dimens.Paddings.smallPadding
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = stringResource(id = R.string.add)
                )
            }
        }
    }
}
