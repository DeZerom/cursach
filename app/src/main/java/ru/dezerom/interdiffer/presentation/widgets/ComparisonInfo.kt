package ru.dezerom.interdiffer.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.utils.toString
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens

@Composable
fun ComparisonInfo(
    comparison: ComparisonModel,
    onClick: (ComparisonModel) -> Unit,
    onDelete: (ComparisonModel) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
        modifier = FullWidthModifier
            .clickable { onClick(comparison) }
            .padding(Dimens.Paddings.basePadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
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
                .fillMaxWidth()
        ) {
            InfoColumn(person = comparison.firstPerson, textAlignment = Alignment.Start)

            Divider(
                modifier = Modifier
                    .width(Dimens.Sizes.dividerThickness)
                    .fillMaxHeight()
            )

            InfoColumn(person = comparison.secondPerson, textAlignment = Alignment.End)
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

@Composable
private fun InfoColumn(
    person: VkUserModel,
    textAlignment: Alignment.Horizontal,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = textAlignment,
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.smallPadding),
        modifier = Modifier
            .width(Dimens.Sizes.smallPhotoSize)
            .then(modifier)
    ) {
        BaseText(text = person.firstName)

        BaseText(text = person.lastName)

        BaseSmallText(text = person.birthDate.toString())
    }
}

@Composable
@Preview
private fun Preview() {
    ComparisonInfo(
        ComparisonModel(
            id = 0,
            firstPerson = VkUserModel(
                id = 0,
                firstName = "John",
                lastName = "Wick",
                isClosed = false,
                deactivationType = DeactivationType.ACTIVE,
                birthDate = null,
                photo100 = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/VK_Compact_Logo_%282021-present%29.svg/1200px-VK_Compact_Logo_%282021-present%29.svg.png",
                photo200 = ""
            ),
            secondPerson = VkUserModel(
                id = 0,
                firstName = "John",
                lastName = "Wick",
                isClosed = false,
                deactivationType = DeactivationType.ACTIVE,
                birthDate = null,
                photo100 = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/VK_Compact_Logo_%282021-present%29.svg/1200px-VK_Compact_Logo_%282021-present%29.svg.png",
                photo200 = ""
            )
        ),
        onClick = {},
        onDelete = {}
    )
}
