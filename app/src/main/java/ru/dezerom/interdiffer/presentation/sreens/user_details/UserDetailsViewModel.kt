package ru.dezerom.interdiffer.presentation.sreens.user_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel
import ru.dezerom.interdiffer.presentation.utils.res.destinations.NestedNavDestinations
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): BaseViewModel() {

    init {
        Timber.e("init")
        viewModelScope.launch {
            Timber.e("scope")
            val name: Int =
                checkNotNull(savedStateHandle[NestedNavDestinations.VkUserDetails.argName])

            setToastText("$name")
        }
    }

    override fun onCriticalErrorClick() {
        TODO("Not yet implemented")
    }

    override fun dropSideEffect() {
        TODO("Not yet implemented")
    }
}
