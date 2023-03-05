package ru.dezerom.interdiffer.presentation.sreens.comparisons

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.interactors.ComparisonsInteractor
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.utils.handle
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ComparisonsViewModel @Inject constructor(
    private val comparisonsInteractor: ComparisonsInteractor
): BaseViewModel() {

    private val _state: MutableStateFlow<ComparisonsScreenState> =
        MutableStateFlow(ComparisonsScreenState.Empty)
    val state = _state.asStateFlow()

    init {
        fetchInfo()
    }

    override fun onCriticalErrorClick() {
        fetchInfo()
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

        val res = comparisonsInteractor.getSavedComparison()

        res.handle(
            onSuccess = {
                _state.value = ComparisonsScreenState.ShowList(it)
                setProgressOrContent(false)
            },
            onError = { handleError(it) }
        )
    }

}
