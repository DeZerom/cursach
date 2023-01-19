package ru.dezerom.interdiffer.presentation.sreens.people

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.utils.toDayMonthYearString
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.utils.Dimens
import ru.dezerom.interdiffer.presentation.utils.MaxSizeModifier
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
                
                FullWidthCard(
                    modifier = Modifier
                        .clickable { viewModel.onItemClick(item) }
                ) {
                    Box(
                        modifier = MaxSizeModifier
                            .padding(Dimens.Paddings.basePadding)
                    ) {
                        Row(
                            modifier = MaxSizeModifier
                                .align(Alignment.TopStart)
                        ) {
                            Image(
                                modifier = Modifier.size(Dimens.Sizes.smallPhotoSize),
                                painter = painterResource(id = R.drawable.ic_people),
                                contentDescription = null
                            )

                            Box(
                                contentAlignment = Alignment.TopStart,
                                modifier = Modifier
                                    .height(Dimens.Sizes.smallPhotoSize)
                                    .padding(start = Dimens.Paddings.halfPadding)
                            ) {
                                Column {
                                    BaseCenteredText(text = item.firstName)
                                    BaseCenteredText(text = item.lastName)
                                }

                                item.birthDate?.toDayMonthYearString()?.let { birthDate ->
                                    BaseSmallText(
                                        text = stringResource(id = R.string.birthDate, birthDate),
                                        modifier = Modifier.align(Alignment.BottomStart)
                                    )
                                }
                            }
                            }

                        Image(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = stringResource(id = R.string.delete),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .clickable { viewModel.onItemDeleteClick() }
                        )
                    }
                }
            }
        }
    }
}
