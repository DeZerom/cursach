package ru.dezerom.interdiffer.presentation.sreens.comparisons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.presentation.dialogs.AddComparisonDialog
import ru.dezerom.interdiffer.presentation.items.ComparisonItem
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.toolbar.Toolbar
import ru.dezerom.interdiffer.presentation.utils.MaxSizeModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseColumnWidget
import ru.dezerom.interdiffer.presentation.widgets.BaseLazyColumn
import ru.dezerom.interdiffer.presentation.widgets.EmptyListWidget
import ru.dezerom.interdiffer.ui.theme.Orange

@Composable
fun ComparisonsScreen(
    viewModel: ComparisonsViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState()
    val sideEffect = viewModel.sideEffect.collectAsState(initial = null)

    when (sideEffect.value) {
        ComparisonsScreenSideEffect.ShowAddComparisonDialog -> {
            val showDialogState = remember { mutableStateOf(true) }
            showDialogState.value = true

            AddComparisonDialog(
                showState = showDialogState,
                onUsersSelect = viewModel::createComparison
            )
        }

        null -> {}
    }

    BaseScreen(
        viewModel = viewModel,
        navController = navController,
        toolbar = {
            Toolbar(title = stringResource(id = R.string.comparisons))
        }
    ) {
        BaseColumnWidget {
            when (val st = state.value) {
                is ComparisonsScreenState.ShowList -> {
                    ShowList(viewModel = viewModel, state = st)
                }

                ComparisonsScreenState.Empty -> {}
            }
        }
    }
}

@Composable
private fun ShowList(
    viewModel: ComparisonsViewModel,
    state: ComparisonsScreenState.ShowList
) {
    if (state.list.isEmpty()) {
        EmptyListWidget(onAddButtonClick = viewModel::onAddItemClick)
    } else {
        Box(
            modifier = MaxSizeModifier
        ) {
            BaseLazyColumn {
                items(
                    count = state.list.size,
                    key = { state.list[it].id }
                ) {
                    ComparisonItem(
                        comparison = state.list[it],
                        onClick = viewModel::onClick,
                        onDelete = viewModel::onDeleteClick
                    )
                }
            }

            FloatingActionButton(
                onClick = viewModel::onAddItemClick,
                backgroundColor = Orange,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = Dimens.Paddings.smallPadding,
                        end = Dimens.Paddings.smallPadding
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_plus_no_bg),
                    contentDescription = stringResource(id = R.string.add)
                )
            }
        }
    }
}