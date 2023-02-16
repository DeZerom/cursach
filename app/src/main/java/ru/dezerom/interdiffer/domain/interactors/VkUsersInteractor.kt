package ru.dezerom.interdiffer.domain.interactors

import ru.dezerom.interdiffer.data.repositoties.VkSocietyRepository
import ru.dezerom.interdiffer.data.repositoties.VkUsersRepository
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import javax.inject.Inject


class VkUsersInteractor @Inject constructor(
    private val usersRepository: VkUsersRepository,
    private val societiesRepository: VkSocietyRepository
) {

    suspend fun addUserByScreenName(screenName: String): Boolean {
        val userId = usersRepository.addUserByScreenName(screenName)

        if (userId !is RequestResult.Success)
            return false

        return societiesRepository.addUserSocieties(userId.data)
    }

    suspend fun getSavedUsers(): RequestResult<List<VkUserModel>> {
        return usersRepository.getSavedUsers()
    }

    suspend fun getUserInfoById(userId: Int): RequestResult<VkUserModel> {
        return usersRepository.getUserInfoById(userId)
    }

    suspend fun deleteVkUser(userId: Int): Boolean {
        return usersRepository.deleteVkUser(userId)
    }

}
