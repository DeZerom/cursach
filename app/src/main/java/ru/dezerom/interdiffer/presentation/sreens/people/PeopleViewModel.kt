package ru.dezerom.interdiffer.presentation.sreens.people

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.data.repositoties.VkUsersRepository
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.models.utils.HandleableError
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import ru.dezerom.interdiffer.presentation.utils.forceSend
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val vkUsersRepository: VkUsersRepository
): BaseViewModel() {

    private val _viewState =
        MutableStateFlow<PeopleScreenState>(PeopleScreenState.ShowingList(listOf()))
    val viewState = _viewState.asStateFlow()

    private val _sideEffect = Channel<PeopleScreenSideEffect?>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        viewModelScope.launch {
            getVkUsers()
        }
    }

    override fun onCriticalErrorClick() {
        viewModelScope.launch {
            getVkUsers()
        }
    }

    fun onAddButtonClick() = viewModelScope.launch {
        _sideEffect.forceSend(PeopleScreenSideEffect.ShowAddUserDialog)
    }

    fun onUserAddButtonClick(userId: String) {
        viewModelScope.launch {
            setProgressOrContent(true)

            when (val result = vkUsersRepository.addUserByScreenName(userId)) {
                is RequestResult.Success -> {
                    if (result.data)
                        getVkUsers()
                    else
                        handleUnknownError()
                }
                is HandleableError ->
                    handleError(result)

                else -> handleUnknownError()
            }
        }
    }

    fun onItemClick(item: VkUserModel) = viewModelScope.launch {
        setToastText(item.firstName)
    }

    fun onItemDeleteClick(item: VkUserModel) {
        viewModelScope.launch {
            setToastText("delete")
        }
    }

    fun onInfoCircleClick() = viewModelScope.launch {
        _sideEffect.forceSend(PeopleScreenSideEffect.ShowInfoCirclesDescription)
    }

    private suspend fun getVkUsers() {
        setProgressOrContent(true)

        when (val result = vkUsersRepository.getSavedUsers()) {
            is RequestResult.Success -> {
                _viewState.value = PeopleScreenState.ShowingList(result.data)
                setProgressOrContent(false)
            }
            is HandleableError -> {
                handleError(result)
            }

            else -> handleUnknownError()
        }
    }
}