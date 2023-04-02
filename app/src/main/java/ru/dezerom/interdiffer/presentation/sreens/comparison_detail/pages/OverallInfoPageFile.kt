package ru.dezerom.interdiffer.presentation.sreens.comparison_detail.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.logic.categorizer.countOfSocieties
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonInvalidationReason
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.comparasion.DetailedComparisonModel
import ru.dezerom.interdiffer.presentation.sreens.comparison_detail.pager.ComparisonDetailViewModel
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.utils.toNearestInt
import ru.dezerom.interdiffer.presentation.widgets.BaseSmallText
import ru.dezerom.interdiffer.presentation.widgets.BaseText
import ru.dezerom.interdiffer.presentation.widgets.ComparisonInfo
import ru.dezerom.interdiffer.ui.theme.BrighterRed
import ru.dezerom.interdiffer.ui.theme.Orange
import ru.dezerom.interdiffer.ui.theme.Red
import ru.dezerom.interdiffer.ui.theme.Shapes

@Composable
fun OverallInfoScreen(
    viewModel: ComparisonDetailViewModel,
    detailedComparison: DetailedComparisonModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.Paddings.basePadding)
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = Shapes.small
                    )
                    .padding(Dimens.Paddings.basePadding)
            ) {
                ComparisonInfo(
                    comparison = ComparisonModel(
                        id = detailedComparison.comparisonId,
                        firstPerson = detailedComparison.firstPerson,
                        secondPerson = detailedComparison.secondPerson
                    ),
                    onClick = {},
                    onDelete = viewModel::onDelete
                )

                if (detailedComparison.invalidationReason != null) {
                    Invalidation(
                        detailedComparison.invalidationReason,
                        modifier = Modifier.padding(top = Dimens.Paddings.largePadding)
                    )
                }

                Common(
                    overallMatch = detailedComparison.overallMatching,
                    categoriesMatches = detailedComparison.categoriesMatching,
                    modifier = Modifier.padding(top = Dimens.Paddings.basePadding)
                )

                OverallInfo(
                    detailedComparison = detailedComparison,
                    modifier = Modifier.padding(top = Dimens.Paddings.basePadding)
                )
            }
        }
    }
}

@Composable
private fun OverallInfo(
    detailedComparison: DetailedComparisonModel,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.smallPadding),
        modifier = modifier
            .fillMaxWidth()
            .border(
                color = Orange,
                width = Dimens.Sizes.dividerThickness,
                shape = Shapes.small
            )
            .padding(Dimens.Paddings.basePadding)
    ) {
        Text(
            text = stringResource(
                id = R.string.subscriptions_count_binary,
                formatArgs = arrayOf(
                    detailedComparison.firstPersonCategorizedSocieties.countOfSocieties(),
                    detailedComparison.secondPersonCategorizedSocieties.countOfSocieties()
                )
            ),
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Text(
            text = stringResource(id = R.string.matches_by_categories),
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        val mergedCategories: Map<String, Pair<Int, Int>> = remember(detailedComparison) {
            buildMap {
                detailedComparison.categoriesMatching.keys.forEach { category ->
                    val firstCount = detailedComparison.firstPersonCategorizedSocieties
                        .find { it.name == category }
                        ?.count ?: 0
                    val secondCount =
                        detailedComparison.secondPersonCategorizedSocieties
                            .find { it.name == category }
                            ?.count ?: 0

                    put(category, Pair(firstCount, secondCount))
                }
            }
        }
        mergedCategories.forEach { (name, counts) ->
            BaseSmallText(
                text = stringResource(
                    id = R.string.category_count_binary,
                    formatArgs = arrayOf(name, counts.first, counts.second)
                ),
                modifier = Modifier.padding(start = Dimens.Paddings.basePadding)
            )
        }
    }
}

@Composable
private fun Invalidation(
    invalidationReason: ComparisonInvalidationReason,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = Dimens.Sizes.dividerThickness,
                color = Red,
                shape = Shapes.small
            )
            .background(
                color = BrighterRed,
                shape = Shapes.small
            )
            .then(modifier)
    ) {
        Text(
            text = stringResource(id = R.string.attention),
            fontWeight = FontWeight.Medium,
            fontSize = Dimens.FontSizes.big
        )

        BaseText(
            text = stringResource(
                id = invalidationReason.descriptionStrRes,
                formatArgs = invalidationReason.formatArgs
            )
        )
    }
}

@Composable
private fun Common(
    overallMatch: Double,
    categoriesMatches: Map<String, Double>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.smallPadding),
        modifier = modifier
            .fillMaxWidth()
            .border(
                color = Orange,
                width = Dimens.Sizes.dividerThickness,
                shape = Shapes.small
            )
            .padding(Dimens.Paddings.basePadding)
    ) {
        Text(
            text = stringResource(
                id = R.string.overall_match, 
                formatArgs = arrayOf(overallMatch.toNearestInt())
            ),
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Text(
            text = stringResource(id = R.string.matches_by_categories),
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        
        categoriesMatches.forEach { (name, match) ->
            BaseSmallText(
                text = stringResource(
                    id = R.string.category_match,
                    formatArgs = arrayOf(name, match.toNearestInt())
                ),
                modifier = Modifier.padding(start = Dimens.Paddings.basePadding)
            )
        }
    }
}
