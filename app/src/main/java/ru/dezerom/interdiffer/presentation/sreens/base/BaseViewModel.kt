package ru.dezerom.interdiffer.presentation.sreens.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.dezerom.interdiffer.presentation.side_effects.BaseSideEffect
import ru.dezerom.interdiffer.presentation.states.BaseScreenState

abstract class BaseViewModel<E: BaseSideEffect, S: BaseScreenState>: ViewModel() {

    private val _sideEffects = Channel<E>(Channel.BUFFERED)
    val sideEffects = _sideEffects.receiveAsFlow()

    private val _screenState = MutableStateFlow<BaseScreenState>(BaseScreenState.ShowingContent)
    val screenState = _screenState.asStateFlow()

    protected fun getInfo() {
        _screenState.value = BaseScreenState.Loading

        fetchInfoAndProceedResult()
    }

    protected abstract fun fetchInfoAndProceedResult()

    

}
