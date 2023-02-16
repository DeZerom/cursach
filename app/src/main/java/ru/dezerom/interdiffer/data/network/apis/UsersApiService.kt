package ru.dezerom.interdiffer.data.network.apis

import retrofit2.http.GET
import retrofit2.http.Query
import ru.dezerom.interdiffer.data.models.VkUserDataModel
import ru.dezerom.interdiffer.data.network.responses.ResponseArrayModel
import ru.dezerom.interdiffer.data.network.responses.ResponseObjectModel
import ru.dezerom.interdiffer.data.network.responses.SocietiesResponse

interface UsersApiService {

    @GET("users.getSubscriptions")
    suspend fun getUserSubscriptions(
        @Query("user_id") userId: Int,
        @Query("offset") offset: Int? = null,
        @Query("count") count: Int? = null,
        @Query("fields") fields: String? = null,
        @Query("extended") extended: Boolean = false
    ): ResponseObjectModel<SocietiesResponse>?

    @GET("users.get")
    suspend fun getUserInfo(
        @Query("user_ids") ids: List<String>,
        @Query("fields") fields: String
    ): ResponseArrayModel<VkUserDataModel>?
}
