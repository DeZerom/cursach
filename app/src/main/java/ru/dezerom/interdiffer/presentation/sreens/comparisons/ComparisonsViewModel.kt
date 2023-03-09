package ru.dezerom.interdiffer.presentation.sreens.comparisons

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.interactors.ComparisonsInteractor
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.utils.handle
import ru.dezerom.interdiffer.presentation.change_listener.comparison.ComparisonsChangeListener
import ru.dezerom.interdiffer.presentation.change_listener.comparison.ComparisonsChangeListenersHolder
import ru.dezerom.interdiffer.presentation.change_listener.comparison.ComparisonsChangePayload
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import ru.dezerom.interdiffer.presentation.utils.forceSend
import javax.inject.Inject

@HiltViewModel
class ComparisonsViewModel @Inject constructor(
    private val comparisonsInteractor: ComparisonsInteractor
): BaseViewModel(), ComparisonsChangeListener {

    private val _state: MutableStateFlow<ComparisonsScreenState> =
        MutableStateFlow(ComparisonsScreenState.Empty)
    val state = _state.asStateFlow()

    private val _sideEffects: MutableSharedFlow<ComparisonsScreenSideEffect?> =
        MutableSharedFlow()
    val sideEffect = _sideEffects.asSharedFlow()

    init {
        ComparisonsChangeListenersHolder.registerListener(this)
        fetchInfo()
    }

    override fun onCleared() {
        ComparisonsChangeListenersHolder.unregisterListener(this)
        super.onCleared()
    }

    override fun onCriticalErrorClick() {
        fetchInfo()
    }

    override fun onChange(payload: ComparisonsChangePayload) {
        when (payload) {
            is ComparisonsChangePayload.ComparisonAdded -> {
                val currentState = state.value
                if (currentState is ComparisonsScreenState.ShowList) {
                    _state.value = currentState.copy(
                        list = currentState.list + payload.comparison
                    )
                }
            }
        }
    }

    fun onAddItemClick() = viewModelScope.launch {
        _sideEffects.forceSend(
            ComparisonsScreenSideEffect.NavigateToCreateComparisonScreen
        )
    }

    fun onClick(item: ComparisonModel) = viewModelScope.launch {
        //todo
    }

    fun onDeleteClick(item: ComparisonModel) = viewModelScope.launch {
        setProgressOrContent(true)

        val st = state.value

        if (st is ComparisonsScreenState.ShowList) {
            if (comparisonsInteractor.deleteComparison(item.id)) {
                _state.value = st.copy(
                    list = st.list - item
                )
            } else {
                setToastText(R.string.something_went_wrong)
            }
        }

        setProgressOrContent(false)
    }

    private fun fetchInfo() = viewModelScope.launch {
        setProgressOrContent(true)

        val res = comparisonsInteractor.getSavedComparisons()

        res.handle(
            onSuccess = {
                _state.value = ComparisonsScreenState.ShowList(it)
                setProgressOrContent(false)
            },
            onError = { handleError(it) }
        )
    }

}
