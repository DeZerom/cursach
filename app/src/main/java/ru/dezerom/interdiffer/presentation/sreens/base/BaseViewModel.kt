package ru.dezerom.interdiffer.presentation.sreens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.domain.models.utils.VkErrorType

abstract class BaseViewModel: ViewModel() {

    private val _baseSideEffect = Channel<BaseSideEffect>(Channel.BUFFERED)
    val baseSideEffect = _baseSideEffect.receiveAsFlow()

    private val _baseScreenState = MutableStateFlow<BaseScreenState>(BaseScreenState.ShowingInfo)
    val baseScreenState = _baseScreenState.asStateFlow()

    abstract fun onCriticalErrorClick()

    protected fun setProgress(isInProgress: Boolean) {
        _baseScreenState.value = if (isInProgress) BaseScreenState.Loading
        else BaseScreenState.ShowingInfo
    }

    protected fun getInfo() = viewModelScope.launch {
        if (fetchInfoAndProcessResult())
            _baseScreenState.value = BaseScreenState.ShowingInfo
        else
            _baseScreenState.value = BaseScreenState.ShowUnknownError
    }

    protected abstract suspend fun fetchInfoAndProcessResult(): Boolean

    protected fun setToastText(text: String) = viewModelScope.launch {
        _baseSideEffect.send(BaseSideEffect.ShowToast(text))
    }

    protected fun handleVkError(type: VkErrorType) {}

}
