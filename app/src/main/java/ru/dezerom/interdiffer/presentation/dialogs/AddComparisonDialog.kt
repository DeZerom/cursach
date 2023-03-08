package ru.dezerom.interdiffer.presentation.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthButton
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard
import ru.dezerom.interdiffer.presentation.widgets.NormalText
import ru.dezerom.interdiffer.ui.theme.Grey

@Composable
fun AddComparisonDialog(
    showState: MutableState<Boolean>,
    onUsersSelect: (VkUserModel, VkUserModel) -> Unit
) {
    val selectedUsers = remember { mutableStateOf(Pair<VkUserModel?, VkUserModel?>(null, null)) }

    BaseDialogScreen(showState = showState) {
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
                    info = selectedUsers.value.first,
                    onClick = remember { {} }
                )

                UserInfoRow(
                    info = selectedUsers.value.second,
                    onClick = remember { {} }
                )

                FullWidthButton(
                    text = stringResource(id = R.string.add),
                    onClick = remember { {
                        onUsersSelect(
                            checkNotNull(selectedUsers.value.first),
                            checkNotNull(selectedUsers.value.second)
                        )
                    } },
                    isEnabled = selectedUsers.value.first != null
                            && selectedUsers.value.second != null
                )
            }
        }
    }
}

@Composable
private fun UserInfoRow(
    info: VkUserModel?,
    onClick: () -> Unit
) {
    if (info == null) {
        NoUserSelected(onClick)
    } else {
        //todo
    }
}

@Composable
private fun NoUserSelected(
    onClick: () -> Unit
) {
    FullWidthCard(
        backgroundColor = Grey,
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
