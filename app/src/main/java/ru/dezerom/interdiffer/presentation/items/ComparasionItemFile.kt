package ru.dezerom.interdiffer.presentation.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.presentation.widgets.ComparisonInfo
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard

@Composable
fun ComparisonItem(
    comparison: ComparisonModel,
    onClick: (ComparisonModel) -> Unit,
    onDelete: (ComparisonModel) -> Unit
) {
    FullWidthCard {
        ComparisonInfo(comparison = comparison, onClick = onClick, onDelete = onDelete)
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
