package ru.dezerom.interdiffer.presentation.sreens.people

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.R
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
            refreshVkUsersList()
        }
    }

    override fun onCriticalErrorClick() {
        viewModelScope.launch {
            refreshVkUsersList()
        }
    }

    override fun dropSideEffect() {
        viewModelScope.launch {
            _sideEffect.send(null)
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
                        refreshVkUsersList()
                    else
                        handleUnknownError()
                }
                is HandleableError ->
                    handleError(result)

                else -> handleUnknownError()
            }
        }
    }

    fun onItemClick(item: VkUserModel) {
        viewModelScope.launch {
            _sideEffect.forceSend(PeopleScreenSideEffect.NavigateToUserDetails(item.fullName)) //todo
        }
    }

    fun onItemDeleteClick(item: VkUserModel) {
        viewModelScope.launch {
            val result = vkUsersRepository.deleteVkUser(item.id)

            if (result)
                refreshVkUsersList()
            else
                setToastText(R.string.something_went_wrong)
        }
    }

    fun onInfoCircleClick() = viewModelScope.launch {
        _sideEffect.forceSend(PeopleScreenSideEffect.ShowInfoCirclesDescription)
    }

    private suspend fun refreshVkUsersList() {
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