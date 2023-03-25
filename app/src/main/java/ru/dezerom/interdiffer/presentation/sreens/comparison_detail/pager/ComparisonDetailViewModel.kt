package ru.dezerom.interdiffer.presentation.sreens.comparison_detail.pager

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.domain.interactors.ComparisonsInteractor
import ru.dezerom.interdiffer.domain.models.utils.handle
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import ru.dezerom.interdiffer.presentation.utils.res.destinations.NestedNavDestinations
import javax.inject.Inject

@HiltViewModel
class ComparisonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val comparisonsInteractor: ComparisonsInteractor
) : BaseViewModel() {

    private val _state: MutableStateFlow<ComparisonDetailScreenState> =
        MutableStateFlow(ComparisonDetailScreenState.Empty)
    val state = _state.asStateFlow()

    private val comparisonId: Int =
        savedStateHandle[NestedNavDestinations.ComparisonDetail.argName] ?: 0

    init {
        viewModelScope.launch {
            getInfo()
        }
    }

    override fun onCriticalErrorClick() {
        viewModelScope.launch {
            getInfo()
        }
    }

    private suspend fun getInfo() {
        setProgressOrContent(true)

        comparisonsInteractor.getComparisonDetailInfo(comparisonId).handle(
            onSuccess = {
                _state.value = ComparisonDetailScreenState.ShowInfo(it)
                setProgressOrContent(false)
            },
            onError = {
                handleError(it)
            }
        )
    }
}
