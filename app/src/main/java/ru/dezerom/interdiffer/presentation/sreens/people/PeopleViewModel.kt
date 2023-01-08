package ru.dezerom.interdiffer.presentation.sreens.people

import kotlinx.coroutines.delay
import ru.dezerom.interdiffer.presentation.sreens.base.BaseViewModel

class PeopleViewModel: BaseViewModel() {

    override fun onCriticalErrorClick() {
        TODO("Not yet implemented")
    }

    override suspend fun fetchInfoAndProcessResult(): Boolean {
        delay(1500L)

        return false
    }

    fun onAddButtonClick() {
        getInfo()
    }
}