package ru.dezerom.interdiffer.presentation.sreens.user_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseSmallText
import ru.dezerom.interdiffer.presentation.widgets.BaseText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard
import ru.dezerom.interdiffer.ui.theme.Shapes

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel) {
    val state = viewModel.state.collectAsState()

    BaseScreen(viewModel = viewModel) {
        when (val st = state.value) {
            is UserDetailsScreenState.ShowDetailsOnly ->
                ShowDetailsOnly(viewModel, st)
            is UserDetailsScreenState.ShowDetailsAndSocieties -> TODO()

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
                    .padding(bottom = Dimens.Paddings.basePadding)
                    .clip(Shapes.small)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.halfPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                ) {
                    BaseText(text = user.lastName)
                    BaseText(text = user.firstName)
                }
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.halfPadding),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
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
                            onClick = viewModel::onRefreshClicked
                        )
                    )
                }
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.smallPadding),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
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
}
