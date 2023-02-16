package ru.dezerom.interdiffer.data.repositoties

import ru.dezerom.interdiffer.data.data_base.dao.UserSocietyRelationsDao
import ru.dezerom.interdiffer.data.data_base.dao.VkUsersDao
import ru.dezerom.interdiffer.data.models.VkUserDataModel
import ru.dezerom.interdiffer.data.network.apis.UsersApiService
import ru.dezerom.interdiffer.data.utils.safeDaoAction
import ru.dezerom.interdiffer.data.utils.safeDaoCall
import ru.dezerom.interdiffer.data.utils.safeVkApiCall
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.mappers.toDomain
import javax.inject.Inject

class VkUsersRepository @Inject constructor(
    private val usersApiService: UsersApiService,
    private val vkUsersDao: VkUsersDao,
    private val userSocietyRelationsDao: UserSocietyRelationsDao
) {

    suspend fun getUserInfoById(id: Int): RequestResult<VkUserModel> {
        return safeDaoCall {
            vkUsersDao.getUserById(id)?.toDomain()
        }
    }

    suspend fun getSavedUsers(): RequestResult<List<VkUserModel>> {
        return safeDaoCall {
            vkUsersDao.getAllVkUsers()?.map { it.toDomain() }
        }
    }

    suspend fun addUserByScreenName(userScreenName: String): RequestResult<Int> {
        val result = safeVkApiCall(
            call = {
                usersApiService.getUserInfo(
                    ids = listOf(userScreenName),
                    fields = USER_DEFAULT_FIELDS
                )
            },
            successMapper = { it.data?.firstOrNull() },
            onNullValue = { RequestResult.Error.NothingFoundError }
        )

        return when (result) {
            is RequestResult.Error -> result
            is RequestResult.Success -> {
                if (RequestResult.Success(writeVkUserToDb(result.data)).data)
                    RequestResult.Success(result.data.id ?: 0)
                else
                    RequestResult.Error.RoomError
            }
        }
    }

    suspend fun deleteVkUser(userId: Int): Boolean {
        return safeDaoAction {
            vkUsersDao.deleteVkUser(userId)
            userSocietyRelationsDao.deleteRelationsByUserId(userId)
        }
    }

    private suspend fun writeVkUserToDb(vkUser: VkUserDataModel): Boolean {
        return safeDaoAction {
            vkUsersDao.insertVkUser(vkUser)
        }
    }

    companion object {
        private const val USER_DEFAULT_FIELDS = "bdate, photo_100, photo_200"
    }

}
