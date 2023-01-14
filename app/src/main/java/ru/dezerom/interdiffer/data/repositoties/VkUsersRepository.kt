package ru.dezerom.interdiffer.data.repositoties

import kotlinx.coroutines.delay
import ru.dezerom.interdiffer.data.network.apis.UsersApiService
import ru.dezerom.interdiffer.data.network.requests.UserRequest
import ru.dezerom.interdiffer.data.utils.safeVkApiCall
import ru.dezerom.interdiffer.domain.models.DeactivationType
import ru.dezerom.interdiffer.domain.models.user.UserModel
import ru.dezerom.interdiffer.domain.models.utils.PartialDate
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.mappers.toDomain
import javax.inject.Inject

class VkUsersRepository @Inject constructor(
    private val usersApiService: UsersApiService
) {

    //todo размокать
    suspend fun getSavedUsers(): RequestResult<List<UserModel>> {
        delay(2000)

        return RequestResult.Success(
            listOf(
                UserModel(1, "Вася", "Иванов", false, DeactivationType.ACTIVE, PartialDate(10, 2, 2000)),
                UserModel(2, "Ваня", "Васичкин", false, DeactivationType.ACTIVE, PartialDate(1, 1, 1990)),
                UserModel(3, "F", "asd", false, DeactivationType.DELETED, null)
            )
        )
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
