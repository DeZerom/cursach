package ru.dezerom.interdiffer.presentation.sreens.user_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.logic.categorizer.countOfSocieties
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.utils.toString
import ru.dezerom.interdiffer.presentation.dialogs.InfoCirclesDescriptionDialogScreen
import ru.dezerom.interdiffer.presentation.dialogs.SocietyDetailsDialog
import ru.dezerom.interdiffer.presentation.items.CategoryName
import ru.dezerom.interdiffer.presentation.items.VkSocietyItem
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.toolbar.Toolbar
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseLazyColumn
import ru.dezerom.interdiffer.presentation.widgets.BaseSmallText
import ru.dezerom.interdiffer.presentation.widgets.BaseText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard
import ru.dezerom.interdiffer.ui.theme.Shapes

@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState()
    val sideEffect = viewModel.sideEffect.collectAsState(null)

    when (val se = sideEffect.value) {
        is UserDetailsSideEffect.ShowInfoCirclesDialog -> {
            val showDialog = remember { mutableStateOf(true) }
            showDialog.value = true

            InfoCirclesDescriptionDialogScreen(showState = showDialog)
        }

        is UserDetailsSideEffect.ShowSocietyDetailsDialog -> {
            val showDialog = remember { mutableStateOf(true) }
            showDialog.value = true

            SocietyDetailsDialog(
                showState = showDialog,
                societyModel = se.society,
                onInfoCirclesClick = viewModel::onInfoCirclesClick
            )
        }

        null -> {}
    }

    BaseScreen(
        viewModel = viewModel,
        navController = navController,
        toolbar = {
            Toolbar(
                title = state.value.userName,
                showBackButton = true,
                onBackButtonClick = viewModel::goBack
            )
        }
    ) {
        when (val st = state.value) {
            is UserDetailsScreenState.ShowDetailsOnly ->
                ShowDetailsOnly(viewModel, st)
            is UserDetailsScreenState.ShowDetailsAndSocieties ->
                ShowDetailsAndSocieties(viewModel = viewModel, state = st)

            UserDetailsScreenState.Empty -> {}
        }
    }
}

@Composable
private fun ShowDetailsOnly(
    viewModel: UserDetailsViewModel,
    state: UserDetailsScreenState.ShowDetailsOnly
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.doublePadding),
        modifier = Modifier.padding(top = Dimens.Paddings.largePadding)
    ) {
        UserDetails(viewModel = viewModel, user = state.details)

        if (state.showSocietiesLoading) {
            Row(horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun ShowDetailsAndSocieties(
    viewModel: UserDetailsViewModel,
    state: UserDetailsScreenState.ShowDetailsAndSocieties
) {
    val items = remember(state) { state.categories }
    val details = remember(state) { state.details }

    BaseLazyColumn {
        item(
            key = details.id
        ) {
            UserDetails(
                viewModel = viewModel,
                user = details,
                subscriptionsCount = items.countOfSocieties()
            )
        }

        items.forEach { category ->
            item(
                key = category.name
            ) {
                CategoryName(name = category.name, count = category.count)
            }

            items(
                count = category.count,
                key = { category.items[it].id }
            ) {
                VkSocietyItem(
                    item = category.items[it],
                    onClick = viewModel::onItemClick,
                    onInfoCirclesClicked = viewModel::onInfoCirclesClick
                )
            }
        }
    }
}

@Composable
private fun UserDetails(
    viewModel: UserDetailsViewModel,
    user: VkUserModel,
    subscriptionsCount: Int? = null
) {
    FullWidthCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Paddings.basePadding)
        ) {
            AsyncImage(
                model = user.photo200,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_people),
                error = painterResource(id = R.drawable.ic_people),
                modifier = Modifier
                    .size(Dimens.Sizes.bigPhotoSize)
                    .clip(Shapes.small)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.Paddings.basePadding)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.halfPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    BaseText(text = user.lastName)
                    BaseText(text = user.firstName)
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.halfPadding),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_refresh),
                        contentDescription = stringResource(id = R.string.refresh),
                        modifier = Modifier.clickable(
                            enabled = true,
                            onClick = viewModel::onRefreshClicked
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = stringResource(id = R.string.delete),
                        modifier = Modifier.clickable(
                            enabled = true,
                            onClick = viewModel::onDeleteClick
                        )
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.smallPadding),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = Dimens.Paddings.basePadding)
            ) {
                BaseSmallText(text = stringResource(
                    id = R.string.birth_date,
                    formatArgs = arrayOf(user.birthDate.toString())
                ))

                subscriptionsCount?.let {
                    BaseSmallText(text = stringResource(
                        id = R.string.subscriptions_count,
                        formatArgs = arrayOf(it)
                    ))
                }
            }
        }
    }
}
