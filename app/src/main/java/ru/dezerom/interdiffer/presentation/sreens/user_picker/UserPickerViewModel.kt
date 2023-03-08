package ru.dezerom.interdiffer.presentation.sreens.user_picker

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel

@HiltViewModel
class UserPickerViewModel: BaseViewModel() {

    private val _state: MutableStateFlow<UserPickerScreenState> =
        MutableStateFlow(UserPickerScreenState.Empty)
    val state = _state.asStateFlow()

    var selectedUsers: Pair<VkUserModel?, VkUserModel?> = Pair(null, null)
        private set

    override fun onCriticalErrorClick() = Unit

    fun onAddButtonClicked() {
        //todo
    }

    fun onInfoCirclesClicked() = viewModelScope.launch {
        //todo
    }

    fun onUserSelected(isPickingFirst: Boolean, user: VkUserModel) {
        //todo
    }

    fun onFirstUserClicked() = viewModelScope.launch {
        //todo
    }

    fun onSecondUserClicked() = viewModelScope.launch {
        //todo
    }

}
