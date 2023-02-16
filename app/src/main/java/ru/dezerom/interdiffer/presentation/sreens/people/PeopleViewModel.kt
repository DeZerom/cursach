package ru.dezerom.interdiffer.presentation.sreens.people

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.interactors.VkUsersInteractor
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.models.utils.handle
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import ru.dezerom.interdiffer.presentation.utils.forceSend
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val vkUsersInteractor: VkUsersInteractor
): BaseViewModel() {

    private val _viewState =
        MutableStateFlow<PeopleScreenState>(PeopleScreenState.ShowingList(listOf()))
    val viewState = _viewState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PeopleScreenSideEffect?>()
    val sideEffect = _sideEffect.asSharedFlow()

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

    fun onAddButtonClick() = viewModelScope.launch {
        _sideEffect.forceSend(PeopleScreenSideEffect.ShowAddUserDialog)
    }

    fun onUserAddButtonClick(userId: String) {
        viewModelScope.launch {
            setProgressOrContent(true)

            if (vkUsersInteractor.addUserByScreenName(userId)) {
                refreshVkUsersList()
            } else {
                handleUnknownError()
            }
        }
    }

    fun onItemClick(item: VkUserModel) {
        viewModelScope.launch {
            _sideEffect.forceSend(PeopleScreenSideEffect.NavigateToUserDetails(item.id))
        }
    }

    fun onItemDeleteClick(item: VkUserModel) {
        viewModelScope.launch {
            val result = vkUsersInteractor.deleteVkUser(item.id)

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

        vkUsersInteractor.getSavedUsers().handle(
            onSuccess = {
                _viewState.value = PeopleScreenState.ShowingList(it)
                setProgressOrContent(false)
            },
            onError = { handleError(it) }
        )
    }
}