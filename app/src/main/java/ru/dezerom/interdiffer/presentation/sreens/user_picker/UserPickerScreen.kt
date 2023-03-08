package ru.dezerom.interdiffer.presentation.sreens.user_picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.presentation.items.VkUserItem
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.toolbar.Toolbar
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.*
import ru.dezerom.interdiffer.ui.theme.Grey
import ru.dezerom.interdiffer.ui.theme.Orange

@Composable
fun UserPickerScreen(
    viewModel: UserPickerViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState()

    BaseScreen(
        viewModel = viewModel,
        navController = navController,
        toolbar = {
            Toolbar(
                title = stringResource(id = R.string.pick_user),
                showBackButton = true,
                onBackButtonClick = viewModel::goBack
            )
        }
    ) {
        BaseColumnWidget {
            when (val st = state.value) {
                is UserPickerScreenState.PickFirst -> PickerWrapper(
                    pickingFirst = true,
                    viewModel = viewModel,
                    users = st.users
                )
                is UserPickerScreenState.PickSecond -> PickerWrapper(
                    pickingFirst = false,
                    viewModel = viewModel,
                    users = st.users
                )

                UserPickerScreenState.Empty -> {}
            }
        }
    }
}

@Composable
private fun PickerWrapper(
    pickingFirst: Boolean,
    viewModel: UserPickerViewModel,
    users: List<VkUserModel>
) {
    val selectedUsers = remember(viewModel) { mutableStateOf(viewModel.selectedUsers) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Paddings.basePadding)
    ) {
        PickedUsersSection(
            pickingFirst = pickingFirst,
            onFirstUserClicked = viewModel::onFirstUserClicked,
            onSecondUserClicked = viewModel::onSecondUserClicked,
            onAddButtonClicked = viewModel::onAddButtonClicked,
            selectedUsers = selectedUsers.value
        )

        UsersList(
            onUserClicked = remember(viewModel) { {viewModel.onUserSelected(pickingFirst, it) } },
            onInfoCirclesClicked = viewModel::onInfoCirclesClicked,
            users = users
        )
    }
}

@Composable
private fun PickedUsersSection(
    pickingFirst: Boolean,
    onFirstUserClicked: () -> Unit,
    onSecondUserClicked: () -> Unit,
    onAddButtonClicked: () -> Unit,
    selectedUsers: Pair<VkUserModel?, VkUserModel?> = Pair(null, null)
) {
    FullWidthCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Paddings.basePadding)
        ) {
            BaseText(
                text = stringResource(id = R.string.adding_comparison),
                fontSize = Dimens.FontSizes.big
            )

            UserInfoRow(
                info = selectedUsers.first,
                onClick = onFirstUserClicked,
                isActive = pickingFirst
            )

            UserInfoRow(
                info = selectedUsers.second,
                onClick = onSecondUserClicked,
                isActive = !pickingFirst
            )

            FullWidthButton(
                text = stringResource(id = R.string.add),
                onClick = onAddButtonClicked,
                isEnabled = selectedUsers.first != null
                        && selectedUsers.second != null
            )
        }
    }
}

@Composable
private fun UsersList(
    onUserClicked: (VkUserModel) -> Unit,
    onInfoCirclesClicked: () -> Unit,
    users: List<VkUserModel>,
    contentPadding: PaddingValues = PaddingValues(top = Dimens.Paddings.largePadding)
) {
    BaseLazyColumn(
        contentPadding = contentPadding
    ) {
        items(
            users.size,
            key = { users[it].id }
        ) {
            VkUserItem(
                model = users[it],
                onClick = onUserClicked,
                onInfoCircleClick = onInfoCirclesClicked,
                onDeleteCircleClick = {},
                showDeleteButton = false
            )
        }
    }
}

@Composable
private fun UserInfoRow(
    info: VkUserModel?,
    onClick: () -> Unit,
    isActive: Boolean
) {
    if (info == null) {
        NoUserSelected(onClick, isActive)
    } else {
        VkUserItem(
            model = info,
            onClick = remember { { onClick() } },
            onInfoCircleClick = {},
            onDeleteCircleClick = {},
            borderColor = if (isActive) Orange else null
        )
    }
}

@Composable
private fun NoUserSelected(
    onClick: () -> Unit,
    isActive: Boolean
) {
    FullWidthCard(
        backgroundColor = Grey,
        borderColor = if (isActive) Orange else null,
        modifier = Modifier
            .height(Dimens.Sizes.smallPhotoSize)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column {
                BaseText(text = stringResource(id = R.string.no_user_selected))
                NormalText(text = stringResource(id = R.string.click_here_to_add))
            }
        }
    }
}
