package ru.dezerom.interdiffer.data.repositoties

import kotlinx.coroutines.delay
import ru.dezerom.interdiffer.data.network.apis.UsersApiService
import ru.dezerom.interdiffer.data.network.requests.UserRequest
import ru.dezerom.interdiffer.data.utils.safeVkApiCall
import ru.dezerom.interdiffer.domain.models.user.VkUserModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.mappers.toDomain
import timber.log.Timber
import javax.inject.Inject

class VkUsersRepository @Inject constructor(
    private val usersApiService: UsersApiService
) {

    //todo размокать
    suspend fun getSavedUsers(): RequestResult<List<VkUserModel>> {
        delay(2000)

        return RequestResult.Success(emptyList())
    }

    suspend fun addUserByScreenName(userScreenName: String): RequestResult<Boolean> {
        val result = safeVkApiCall(
            call = {
                usersApiService.getUserInfo(
                    body = UserRequest(
                        screenNames = listOf(userScreenName),
                        fields = USER_DEFAULT_FIELDS
                    )
                )
            },
            successMapper = { it.data?.firstOrNull()?.toDomain() }
        )

        return when (result) {
            is RequestResult.Error -> result
            is RequestResult.Success -> {
                val user = result.data
                Timber.e("$user")

                RequestResult.Success(true)
            }
        }
    }

    companion object {
        private const val USER_DEFAULT_FIELDS = "bdate, photo_100, photo_200"
    }

}
