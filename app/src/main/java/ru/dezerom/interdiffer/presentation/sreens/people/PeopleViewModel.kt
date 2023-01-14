package ru.dezerom.interdiffer.presentation.sreens.people

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.dezerom.interdiffer.data.repositoties.VkUsersRepository
import ru.dezerom.interdiffer.domain.models.user.UserModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val usersRepository: VkUsersRepository
): BaseViewModel() {

    private val _viewState =
        MutableStateFlow<PeopleScreenState>(PeopleScreenState.ShowingList(listOf()))
    val viewState = _viewState.asStateFlow()

    init {
        getInfo()
    }

    override fun onCriticalErrorClick() {
        setToastText("qweqwe")
    }

    override suspend fun fetchInfoAndProcessResult(): Boolean {
        setProgress(true)

        return when (val result = usersRepository.getSavedUsers()) {
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

    fun onAddButtonClick() {
        setToastText("asdasd")
    }

    fun onItemClick(item: UserModel) {
        setToastText(item.firstName)
    }

    fun onItemDeleteClick() {
        setToastText("delete")
    }
}