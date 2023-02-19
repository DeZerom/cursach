package ru.dezerom.interdiffer.presentation.sreens.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.dezerom.interdiffer.domain.models.utils.HandleableError
import ru.dezerom.interdiffer.domain.models.utils.RequestResult

abstract class BaseViewModel: ViewModel() {

    private val _baseSideEffect = Channel<BaseSideEffect>(Channel.BUFFERED)
    val baseSideEffect = _baseSideEffect.receiveAsFlow()

    private val _baseScreenState = MutableStateFlow<BaseScreenState>(BaseScreenState.ShowingInfo)
    val baseScreenState = _baseScreenState.asStateFlow()

    abstract fun onCriticalErrorClick()

    protected fun setProgressOrContent(isProgress: Boolean) {
        if (isProgress)
            _baseScreenState.value = BaseScreenState.Loading
        else
            _baseScreenState.value = BaseScreenState.ShowingInfo
    }

    protected fun handleUnknownError() {
        _baseScreenState.value = BaseScreenState.showUnknownError()
    }

    protected suspend fun setToastText(text: String) {
        _baseSideEffect.send(BaseSideEffect.ShowToast.ByText(text))
    }

    protected suspend fun setToastText(@StringRes textRes: Int) {
        _baseSideEffect.send(BaseSideEffect.ShowToast.ByStringRes(textRes))
    }

    protected fun handleError(
        error: RequestResult.Error
    ) {
        if (error is HandleableError)
            _baseScreenState.value =
                BaseScreenState.ShowingError(error.title, error.message)
        else
            handleUnknownError()
    }

    protected suspend fun goBack() {
        _baseSideEffect.send(BaseSideEffect.GoBack)
    }

}
