package ru.dezerom.interdiffer.presentation.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.society.ClosedType
import ru.dezerom.interdiffer.domain.models.society.SocietyAgeLimits
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.MaxSizeModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseBigCenteredText
import ru.dezerom.interdiffer.presentation.widgets.BaseSmallText
import ru.dezerom.interdiffer.presentation.widgets.BaseText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard
import ru.dezerom.interdiffer.ui.theme.Orange
import ru.dezerom.interdiffer.ui.theme.Shapes

@Composable
fun VkSocietyItem(
    item: VkSocietyModel,
    onClick: (VkSocietyModel) -> Unit,
    onInfoCirclesClicked: () -> Unit
) {
    FullWidthCard(modifier = Modifier.clickable { onClick(item) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
            modifier = MaxSizeModifier.padding(Dimens.Paddings.basePadding)
        ) {
            AsyncImage(
                model = item.photo100,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_society),
                error = painterResource(id = R.drawable.ic_society),
                modifier = Modifier
                    .size(Dimens.Sizes.smallPhotoSize)
                    .clip(Shapes.small)
            )

            Column(
                modifier = MaxSizeModifier
            ) {
                BaseBigCenteredText(
                    text = stringResource(id = item.type.toStrRes()),
                    modifier = Modifier.padding(bottom = Dimens.Paddings.halfPadding),
                    maxLines = 1
                )

                BaseText(
                    text = item.name,
                    modifier = Modifier.padding(bottom = Dimens.Paddings.halfPadding),
                    maxLines = 1
                )

                BaseSmallText(
                    text = item.description,
                    modifier = Modifier.padding(bottom = Dimens.Paddings.halfPadding),
                    maxLines = 2
                )
                
                Row {
                    val ageLimitsRes = when (item.ageLimits) {
                        SocietyAgeLimits.SIXTEEN -> R.drawable.ic_sixteen_age_limit
                        SocietyAgeLimits.EIGHTEEN -> R.drawable.ic_eighteen_age_limit
                        SocietyAgeLimits.NONE -> 0
                    }

                    if (item.ageLimits != SocietyAgeLimits.NONE) {
                        Image(
                            painter = painterResource(id = ageLimitsRes),
                            contentDescription = null,
                            modifier = Modifier.clickable { onInfoCirclesClicked() }
                        )
                    }

                    if (item.closedType != ClosedType.OPEN) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_closed),
                            contentDescription = stringResource(id = R.string.closed_info_circle),
                            modifier = Modifier.clickable { onInfoCirclesClicked() }
                        )
                    }

                    val bannedRes = when (item.deactivationType) {
                        DeactivationType.ACTIVE -> 0
                        DeactivationType.DELETED -> R.drawable.ic_deleted
                        DeactivationType.BANNED -> R.drawable.ic_banned
                    }

                    if (item.deactivationType != DeactivationType.ACTIVE) {
                        Image(
                            painter = painterResource(id = bannedRes),
                            contentDescription = null,
                            modifier = Modifier.clickable { onInfoCirclesClicked() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryName(
    name: String,
    count: Int
) {
    Column(modifier = FullWidthModifier) {
        BaseBigCenteredText(
            text = stringResource(id = R.string.category, name, count),
            modifier = Modifier
        )

        Divider(
            color = Orange,
            thickness = Dimens.Sizes.dividerThickness
        )
    }
}
