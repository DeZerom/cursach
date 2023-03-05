package ru.dezerom.interdiffer.presentation.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.utils.toString
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseSmallText
import ru.dezerom.interdiffer.presentation.widgets.BaseText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard
import ru.dezerom.interdiffer.presentation.widgets.Photo100

@Composable
fun ComparisonScreen(
    comparison: ComparisonModel,
    onClick: (ComparisonModel) -> Unit,
    onDelete: (ComparisonModel) -> Unit
) {
    FullWidthCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
            modifier = FullWidthModifier
                .clickable { onClick(comparison) }
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.Paddings.largePadding),
                verticalAlignment = Alignment.CenterVertically,
                modifier = FullWidthModifier
            ) {
                Photo100(
                    model = comparison.firstPerson.photo100,
                    placeholder = R.drawable.ic_people
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_vs_orange),
                    contentDescription = null
                )

                Photo100(
                    model = comparison.secondPerson.photo100,
                    placeholder = R.drawable.ic_people
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                InfoColumn(person = comparison.firstPerson, alignment = Alignment.Start)

                Divider(
                    modifier = Modifier.fillMaxWidth()
                )

                InfoColumn(person = comparison.secondPerson, alignment = Alignment.End)
            }

            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = stringResource(id = R.string.delete),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onDelete(comparison) }
            )
        }
    }
}

@Composable
private fun InfoColumn(
    person: VkUserModel,
    alignment: Alignment.Horizontal
) {
    Column(
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.smallPadding)
    ) {
        BaseText(text = person.firstName)

        BaseText(text = person.lastName)

        BaseSmallText(text = person.birthDate.toString())
    }
}
