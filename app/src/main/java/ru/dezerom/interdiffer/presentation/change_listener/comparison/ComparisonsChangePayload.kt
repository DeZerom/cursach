package ru.dezerom.interdiffer.presentation.change_listener.comparison

import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel

sealed class ComparisonsChangePayload {

    class ComparisonAdded(val comparison: ComparisonModel) : ComparisonsChangePayload()

}
