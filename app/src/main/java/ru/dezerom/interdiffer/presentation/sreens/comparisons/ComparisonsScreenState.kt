package ru.dezerom.interdiffer.presentation.sreens.comparisons

import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel

sealed interface ComparisonsScreenState {

    object Empty: ComparisonsScreenState

    data class ShowList(val list: List<ComparisonModel>): ComparisonsScreenState

}