package ru.dezerom.interdiffer.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    private val _baseSideEffect = Channel<BaseSideEffect>(Channel.BUFFERED)
    val baseSideEffect = _baseSideEffect.receiveAsFlow()

    private val _baseScreenState = MutableStateFlow<BaseScreenState>(BaseScreenState.ShowingInfo)
    val baseScreenState = _baseScreenState.asStateFlow()

    protected fun getInfo() = viewModelScope.launch {
        _baseScreenState.value = BaseScreenState.Loading

        if (fetchInfoAndProcessResult())
            _baseScreenState.value = BaseScreenState.ShowingInfo
        else
            _baseScreenState.value = BaseScreenState.ShowUnknownError
    }

    protected abstract suspend fun fetchInfoAndProcessResult(): Boolean

    protected fun setToastText(text: String) = viewModelScope.launch {
        _baseSideEffect.send(BaseSideEffect.ShowToast(text))
    }

}
