package ru.dezerom.interdiffer.presentation.sreens.user_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.domain.interactors.VkUsersInteractor
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.domain.models.utils.handle
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import ru.dezerom.interdiffer.presentation.utils.res.destinations.NestedNavDestinations
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val vkUsersInteractor: VkUsersInteractor
) : BaseViewModel() {

    private val _state = MutableStateFlow<UserDetailsScreenState>(UserDetailsScreenState.Empty)
    val state = _state.asStateFlow()

    private val userId: Int =
        checkNotNull(savedStateHandle[NestedNavDestinations.VkUserDetails.argName])

    init {
        refreshInfo()
    }

    override fun onCriticalErrorClick() {
        refreshInfo()
    }

    fun onDeleteClick() {

    }

    fun onRefreshClicked() {

    }

    private fun refreshInfo() = viewModelScope.launch {
        setProgressOrContent(true)

        val userDetails = vkUsersInteractor.getUserInfoById(userId)
        if (!handleUserDetailsRequestResult(userDetails))
            return@launch

        setProgressOrContent(false)
        //todo подгрузка подписок
    }

    private fun handleUserDetailsRequestResult(requestResult: RequestResult<VkUserModel>): Boolean {
        var result = false
        requestResult.handle(
            onSuccess = {
                _state.value = UserDetailsScreenState.ShowDetailsOnly(it, true)
                result = true
            },
            onError = { handleError(it) }
        )

        return result
    }
}
