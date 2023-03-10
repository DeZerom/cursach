package ru.dezerom.interdiffer.presentation.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.utils.toDayMonthYearString
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseCenteredText
import ru.dezerom.interdiffer.presentation.widgets.BaseSmallText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard
import ru.dezerom.interdiffer.ui.theme.Shapes

@Composable
fun VkUserItem(
    model: VkUserModel,
    onClick: (VkUserModel) -> Unit,
    onInfoCircleClick: () -> Unit,
    onDeleteCircleClick: (VkUserModel) -> Unit,
    borderColor: Color? = null,
    showDeleteButton: Boolean = true
) {

    FullWidthCard(
        borderColor = borderColor,
        modifier = Modifier
            .clickable { onClick(model) }
    ) {
        Box(
            modifier = FullWidthModifier
                .padding(Dimens.Paddings.basePadding)
        ) {
            Row(
                modifier = FullWidthModifier
                    .align(Alignment.TopStart)
            ) {
                AsyncImage(
                    model = model.photo100,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.ic_people),
                    error = painterResource(id = R.drawable.ic_people),
                    modifier = Modifier
                        .size(Dimens.Sizes.smallPhotoSize)
                        .clip(Shapes.small)
                )

                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .height(Dimens.Sizes.smallPhotoSize)
                        .padding(start = Dimens.Paddings.halfPadding)
                ) {
                    Column {
                        BaseCenteredText(text = model.firstName)
                        BaseCenteredText(text = model.lastName)
                    }

                    model.birthDate?.toDayMonthYearString()?.let { birthDate ->
                        BaseSmallText(
                            text = stringResource(id = R.string.birthDate, birthDate),
                            modifier = Modifier.align(Alignment.BottomStart)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                if (showDeleteButton) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = stringResource(id = R.string.delete),
                        modifier = Modifier
                            .clickable { onDeleteCircleClick(model) }
                    )
                }

                val deactivationIcon = when (model.deactivationType) {
                    DeactivationType.ACTIVE -> null
                    DeactivationType.DELETED -> R.drawable.ic_deleted
                    DeactivationType.BANNED -> R.drawable.ic_banned
                }

                deactivationIcon?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = Dimens.Paddings.halfPadding)
                            .clickable { onInfoCircleClick() }
                    )
                }
            }
        }
    }

}
