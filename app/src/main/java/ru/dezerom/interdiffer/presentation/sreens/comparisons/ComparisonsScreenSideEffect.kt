package ru.dezerom.interdiffer.presentation.sreens.comparisons

sealed interface ComparisonsScreenSideEffect {

    object NavigateToCreateComparisonScreen: ComparisonsScreenSideEffect

    class NavigateToComparisonDetailScreen(
        val comparisonId: Int
    ): ComparisonsScreenSideEffect

}
