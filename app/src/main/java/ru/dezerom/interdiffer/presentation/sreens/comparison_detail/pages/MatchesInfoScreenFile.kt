package ru.dezerom.interdiffer.presentation.sreens.comparison_detail.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.comparasion.DetailedComparisonModel
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.presentation.items.VkSocietyItem
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseBigCenteredText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard
import ru.dezerom.interdiffer.presentation.widgets.HeadingCenteredText

@Composable
fun MatchesInfoScreen(
    detailedComparison: DetailedComparisonModel
) {
    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        FullWidthCard(
            modifier = Modifier.padding(vertical = Dimens.Paddings.basePadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.doublePadding),
                modifier = Modifier.padding(Dimens.Paddings.basePadding)
            ) {
                WeakMatches(matches = detailedComparison.weakMatches)

                StrongMatches(matches = detailedComparison.strongMatches)
            }
        }
    }
}

@Composable
private fun WeakMatches(
    matches: List<String>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        HeadingCenteredText(
            text = stringResource(id = R.string.weak_matches),
            modifier = Modifier
        )

        matches.forEach {
            BaseBigCenteredText(
                text = it,
                modifier = Modifier.padding(top = Dimens.Paddings.basePadding)
            )
        }
    }
}

@Composable
private fun StrongMatches(
    matches: List<VkSocietyModel>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        HeadingCenteredText(
            text = stringResource(id = R.string.strong_matches),
            modifier = Modifier
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.Paddings.basePadding)
        ) {
            matches.forEach {
                VkSocietyItem(
                    item = it,
                    onClick = {},
                    onInfoCirclesClicked = {}
                )
            }
        }
    }
}
