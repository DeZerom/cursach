package ru.dezerom.interdiffer.presentation.sreens.comparison_detail.pager

import androidx.compose.runtime.Immutable
import ru.dezerom.interdiffer.domain.models.comparasion.DetailedComparisonModel


sealed interface ComparisonDetailScreenState {

    object Empty: ComparisonDetailScreenState

    @Immutable
    data class ShowInfo(
        val detailedComparison: DetailedComparisonModel
    ) : ComparisonDetailScreenState

}
