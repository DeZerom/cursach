package ru.dezerom.interdiffer.data.repositoties

import kotlinx.coroutines.delay
import ru.dezerom.interdiffer.data.network.apis.UsersApiService
import ru.dezerom.interdiffer.data.network.requests.UserRequest
import ru.dezerom.interdiffer.data.utils.safeVkApiCall
import ru.dezerom.interdiffer.domain.models.user.UserModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.domain.models.utils.VkErrorType
import ru.dezerom.interdiffer.mappers.toDomain
import javax.inject.Inject

class VkUsersRepository @Inject constructor(
    private val usersApiService: UsersApiService
) {

    //todo размокать
    suspend fun getSavedUsers(): RequestResult<List<UserModel>> {
        delay(2000)

        return RequestResult.Error.VkError(VkErrorType.PRIVATE_PROFILE)
    }

    suspend fun getUserByScreenName(screenName: String): RequestResult<UserModel> {
        return safeVkApiCall(
            call = {
                usersApiService.getUserInfo(
                    body = UserRequest(
                        screenNames = listOf(screenName),
                        fields = USER_DEFAULT_FIELDS
                    )
                )
            },
            successMapper = {
                it.data?.firstOrNull()?.toDomain()
            }
        )
    }

    companion object {
        private const val USER_DEFAULT_FIELDS = "bdate, photo_100, photo_200"
    }

}
