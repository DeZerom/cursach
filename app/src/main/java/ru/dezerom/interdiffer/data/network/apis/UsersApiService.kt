package ru.dezerom.interdiffer.data.network.apis

import retrofit2.http.Body
import retrofit2.http.POST
import ru.dezerom.interdiffer.data.models.UserDataModel
import ru.dezerom.interdiffer.data.network.requests.SocietiesRequest
import ru.dezerom.interdiffer.data.network.requests.UserRequest
import ru.dezerom.interdiffer.data.network.responses.ResponseArrayModel
import ru.dezerom.interdiffer.data.network.responses.ResponseObjectModel
import ru.dezerom.interdiffer.data.network.responses.SocietiesResponse

interface UsersApiService {

    @POST("users.getSubscriptions")
    suspend fun getUserSubscriptions(
        @Body body: SocietiesRequest
    ): ResponseObjectModel<SocietiesResponse>?

    @POST("users.get")
    suspend fun getUserInfo(
        @Body body: UserRequest
    ): ResponseArrayModel<UserDataModel>?
}
