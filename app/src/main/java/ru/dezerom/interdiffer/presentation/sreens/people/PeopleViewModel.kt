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
        getInfo()
    }

    override fun onCriticalErrorClick() {
        setToastText("qweqwe")
    }

    override suspend fun fetchInfoAndProcessResult(): Boolean {
        return getVkUsers()
    }

    fun onAddButtonClick() = viewModelScope.launch {
        _sideEffect.forceSend(PeopleScreenSideEffect.ShowAddUserDialog)
    }

    fun onUserAddButtonClick(userId: String) {
        viewModelScope.launch {
            setProgress()

            when (val result = vkUsersRepository.addUserByScreenName(userId)) {
                is RequestResult.Success -> {
                    if (!getVkUsers())
                        handleUnknownError()
                }
                is RequestResult.Error.VkError ->
                    handleVkError(result.type)

                else -> handleUnknownError()
            }
        }
    }

    fun onItemClick(item: VkUserModel) = viewModelScope.launch {
        setToastText(item.firstName)
    }

    fun onItemDeleteClick(item: VkUserModel) {
        setToastText("delete")
    }

    fun onInfoCircleClick() = viewModelScope.launch {
        _sideEffect.forceSend(PeopleScreenSideEffect.ShowInfoCirclesDescription)
    }

    private suspend fun getVkUsers(): Boolean {
        setProgress()

        return when (val result = vkUsersRepository.getSavedUsers()) {
            is RequestResult.Success -> {
                _viewState.value = PeopleScreenState.ShowingList(result.data)
                true
            }
            is RequestResult.Error.VkError -> {
                handleVkError(result.type)
                true
            }
            else -> false
        }
    }
}