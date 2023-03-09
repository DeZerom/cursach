package ru.dezerom.interdiffer.presentation.sreens.user_picker

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.interactors.ComparisonsInteractor
import ru.dezerom.interdiffer.domain.interactors.VkUsersInteractor
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.models.utils.handle
import ru.dezerom.interdiffer.domain.models.utils.suspendHandle
import ru.dezerom.interdiffer.presentation.change_listener.comparison.ComparisonsChangeListenersHolder
import ru.dezerom.interdiffer.presentation.change_listener.comparison.ComparisonsChangePayload
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import ru.dezerom.interdiffer.presentation.utils.forceSend
import javax.inject.Inject

@HiltViewModel
class UserPickerViewModel @Inject constructor(
    private val vkUsersInteractor: VkUsersInteractor,
    private val comparisonsInteractor: ComparisonsInteractor
): BaseViewModel() {

    private val _state: MutableStateFlow<UserPickerScreenState> =
        MutableStateFlow(UserPickerScreenState.Empty)
    val state = _state.asStateFlow()

    private val _sideEffects: MutableSharedFlow<UserPickerSideEffect?> = MutableSharedFlow()
    val sideEffect = _sideEffects.asSharedFlow()

    private var selectedUsers: Pair<VkUserModel?, VkUserModel?> = Pair(null, null)

    init {
        getUsers()
    }

    override fun onCriticalErrorClick() {
        getUsers()
    }

    fun onAddButtonClicked() = viewModelScope.launch {
        if (selectedUsers.first != null && selectedUsers.second != null) {
            comparisonsInteractor.createComparison(
                firstUserId = checkNotNull(selectedUsers.first).id,
                secondUserId = checkNotNull(selectedUsers.second).id
            ).suspendHandle(
                onSuccess = {
                    ComparisonsChangeListenersHolder.triggerChange(
                        payload = ComparisonsChangePayload.ComparisonAdded(it)
                    )
                    setToastText(R.string.comparison_added)

                    sendGoBackSideEffect()
                },
                onError = { setToastText(R.string.something_went_wrong) }
            )
        } else {
            setToastText(R.string.pick_two_users)
        }
    }

    fun onInfoCirclesClicked() = viewModelScope.launch {
        _sideEffects.forceSend(UserPickerSideEffect.ShowInfoCirclesDialog)
    }

    fun onUserSelected(isPickingFirst: Boolean, user: VkUserModel) {
        if (isPickingFirst) {
            selectedUsers = selectedUsers.copy(first = user)

            reducePickingState {
                it.copy(
                    isPickingFirst = false,
                    firstPickedUser = user,
                    isAddingAllowed = isAddingAllowed()
                )
            }
        } else {
            selectedUsers = selectedUsers.copy(second = user)

            reducePickingState {
                it.copy(
                    isPickingFirst = false,
                    secondPickedUser = user,
                    isAddingAllowed = isAddingAllowed()
                )
            }
        }
    }

    fun onFirstUserClicked() {
        reducePickingState {
            it.copy(isPickingFirst = true)
        }
    }

    fun onSecondUserClicked() {
        reducePickingState {
            it.copy(isPickingFirst = false)
        }
    }

    private fun getUsers() = viewModelScope.launch {
        setProgressOrContent(true)

        vkUsersInteractor.getSavedUsers().handle(
            onSuccess = {
                _state.value = UserPickerScreenState.PickingUsers(users = it, isPickingFirst = true)
                setProgressOrContent(false)
            },
            onError = { handleError(it) }
        )
    }

    private fun reducePickingState(
        block: (UserPickerScreenState.PickingUsers) -> UserPickerScreenState.PickingUsers
    ) {
        val currState = state.value as? UserPickerScreenState.PickingUsers ?: return

        _state.value = block(currState)
    }

    private fun isAddingAllowed(): Boolean =
        selectedUsers.first != null && selectedUsers.second != null

}
