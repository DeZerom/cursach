package ru.dezerom.interdiffer.presentation.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.presentation.utils.Dimens
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.widgets.BaseText
import ru.dezerom.interdiffer.presentation.widgets.BoldExtraBigText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard

@Composable
fun InfoCirclesDescriptionDialogScreen(showState: MutableState<Boolean>) {
    BaseDialogScreen(showState) {
        FullWidthCard {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = FullWidthModifier
            ) {
                BoldExtraBigText(
                    text = stringResource(id = R.string.info_circles_description),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(Dimens.Paddings.halfPadding)
                        .padding(bottom = Dimens.Paddings.halfPadding)
                )

                val info = listOf(
                    Pair(R.drawable.ic_sixteen_age_limit, R.string.sixteen_age_limit),
                    Pair(R.drawable.ic_eighteen_age_limit, R.string.eighteen_age_limit),
                    Pair(R.drawable.ic_deleted, R.string.deleted_info_circle),
                    Pair(R.drawable.ic_banned, R.string.banned_info_circle),
                    Pair(R.drawable.ic_closed, R.string.closed_info_circle)
                )

                info.forEachIndexed { index, item ->
                    InfoRow(
                        info = item,
                        isLast = index == info.size - 1
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(info: Pair<Int, Int>, isLast: Boolean) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = FullWidthModifier.padding(
            horizontal = Dimens.Paddings.basePadding,
            vertical = Dimens.Paddings.smallPadding
        )
    ) {
        Row {
            Image(
                painter = painterResource(id = info.first),
                contentDescription = null,
                modifier = Modifier.padding(end = Dimens.Paddings.basePadding)
            )

            BaseText(
                text = stringResource(id = info.second)
            )
        }

        if (!isLast) {
            Divider(
                thickness = Dimens.Sizes.dividerThickness
            )
        }
    }
}
