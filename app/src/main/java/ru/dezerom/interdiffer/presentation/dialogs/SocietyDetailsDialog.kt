package ru.dezerom.interdiffer.presentation.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.society.ClosedType
import ru.dezerom.interdiffer.domain.models.society.SocietyAgeLimits
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseBigCenteredText
import ru.dezerom.interdiffer.presentation.widgets.BaseBoldText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard
import ru.dezerom.interdiffer.presentation.widgets.NormalText
import ru.dezerom.interdiffer.ui.theme.Shapes

@Composable
fun SocietyDetailsDialog(
    showState: MutableState<Boolean>,
    societyModel: VkSocietyModel,
    onInfoCirclesClick: () -> Unit
) {
    BaseDialogScreen(showState = showState) {
        FullWidthCard {
            Column(
                modifier = FullWidthModifier.padding(Dimens.Paddings.basePadding)
            ) {
                BaseBigCenteredText(
                    text = stringResource(id = R.string.society),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = Dimens.Paddings.largePadding)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
                    modifier = FullWidthModifier.padding(bottom = Dimens.Paddings.halfPadding)
                ) {
                    AsyncImage(
                        model = societyModel.photo100,
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.ic_society),
                        error = painterResource(id = R.drawable.ic_society),
                        modifier = Modifier
                            .size(Dimens.Sizes.smallPhotoSize)
                            .clip(Shapes.small)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.halfPadding)
                    ) {
                        BaseBoldText(
                            text = stringResource(id = societyModel.type.toStrRes()),
                            maxLines = 1
                        )

                        BaseBoldText(text = societyModel.activity)

                        NormalText(
                            text = societyModel.name,
                            maxLines = 2,
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Paddings.halfPadding),
                    modifier = Modifier.padding(bottom = Dimens.Paddings.basePadding)
                ) {
                    val ageLimits = when (societyModel.ageLimits) {
                        SocietyAgeLimits.NONE -> 0
                        SocietyAgeLimits.SIXTEEN -> R.drawable.ic_sixteen_age_limit
                        SocietyAgeLimits.EIGHTEEN -> R.drawable.ic_eighteen_age_limit
                    }

                    if (ageLimits != 0) {
                        Image(
                            painter = painterResource(id = ageLimits),
                            contentDescription = null,
                            modifier = Modifier.clickable { onInfoCirclesClick() }
                        )
                    }

                    if (societyModel.closedType != ClosedType.OPEN) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_closed),
                            contentDescription = stringResource(id = R.string.closed_info_circle),
                            modifier = Modifier.clickable { onInfoCirclesClick() }
                        )
                    }

                    val bannedRes = when (societyModel.deactivationType) {
                        DeactivationType.ACTIVE -> 0
                        DeactivationType.DELETED -> R.drawable.ic_deleted
                        DeactivationType.BANNED -> R.drawable.ic_banned
                    }

                    if (bannedRes != 0) {
                        Image(
                            painter = painterResource(id = bannedRes),
                            contentDescription = null,
                            modifier = Modifier.clickable { onInfoCirclesClick() }
                        )
                    }
                }

                NormalText(
                    text = societyModel.description,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}
