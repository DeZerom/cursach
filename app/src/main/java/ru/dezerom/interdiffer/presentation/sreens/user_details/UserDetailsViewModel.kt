package ru.dezerom.interdiffer.presentation.sreens.user_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.interactors.VkUsersInteractor
import ru.dezerom.interdiffer.domain.logic.categorizer.categorizeSocieties
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.domain.models.utils.handle
import ru.dezerom.interdiffer.presentation.change_listener.vk_user.VkUsersChangeListenersHolder
import ru.dezerom.interdiffer.presentation.change_listener.vk_user.VkUsersPayload
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import ru.dezerom.interdiffer.presentation.utils.forceSend
import ru.dezerom.interdiffer.presentation.utils.res.destinations.NestedNavDestinations
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val vkUsersInteractor: VkUsersInteractor
) : BaseViewModel() {

    private val _state = MutableStateFlow<UserDetailsScreenState>(UserDetailsScreenState.Empty)
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<UserDetailsSideEffect?>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val userId: Int =
        checkNotNull(savedStateHandle[NestedNavDestinations.VkUserDetails.argName])

    init {
        refreshInfo()
    }

    override fun onCriticalErrorClick() {
        refreshInfo()
    }

    fun onInfoCirclesClick() {
        viewModelScope.launch {
            _sideEffect.forceSend(UserDetailsSideEffect.ShowInfoCirclesDialog)
        }
    }

    fun onItemClick(item: VkSocietyModel) {
        viewModelScope.launch {
            setToastText("Здесь будет подробная информация") //todo
        }
    }

    fun onDeleteClick() {
        viewModelScope.launch {
            if (vkUsersInteractor.deleteVkUser(userId)) {
                VkUsersChangeListenersHolder.triggerChange(VkUsersPayload.UserDeleted(userId))
                sendGoBackSideEffect()
            } else {
                setToastText(R.string.something_went_wrong)
            }
        }
    }

    fun onRefreshClicked() {
        refreshInfo(reload = true)
    }

    private fun refreshInfo(reload: Boolean = false) = viewModelScope.launch {
        setProgressOrContent(true)

        val userDetailsResult = vkUsersInteractor.getUserInfoById(userId = userId, reload = reload)
        val userDetails = handleUserDetailsRequestResult(userDetailsResult) ?: return@launch

        setProgressOrContent(false)

        val subscriptionsResult = vkUsersInteractor.getUserSubscriptions(
            userId = userDetails.id,
            reload = reload
        )
        handleSubscriptionsRequestResult(userDetails, subscriptionsResult)
    }

    private fun handleUserDetailsRequestResult(
        requestResult: RequestResult<VkUserModel>
    ): VkUserModel? {
        var result: VkUserModel? = null
        requestResult.handle(
            onSuccess = {
                _state.value = UserDetailsScreenState.ShowDetailsOnly(it, true)
                result = it
            },
            onError = { handleError(it) }
        )

        return result
    }

    private fun handleSubscriptionsRequestResult(
        userDetails: VkUserModel,
        requestResult: RequestResult<List<VkSocietyModel>>
    ) {
        requestResult.handle(
            onSuccess = {
                _state.value = UserDetailsScreenState.ShowDetailsAndSocieties(
                    details = userDetails,
                    categories = categorizeSocieties(it)
                )
            },
            onError = { handleError(it) }
        )
    }
}
