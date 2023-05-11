package ru.dezerom.interdiffer.data.repositoties

import ru.dezerom.interdiffer.data.data_base.dao.UserSocietyRelationsDao
import ru.dezerom.interdiffer.data.data_base.dao.VkSocietiesDao
import ru.dezerom.interdiffer.data.models.UserSocietyRelationDataModel
import ru.dezerom.interdiffer.data.models.VkSocietyDataModel
import ru.dezerom.interdiffer.data.network.apis.UsersApiService
import ru.dezerom.interdiffer.data.utils.safeDaoAction
import ru.dezerom.interdiffer.data.utils.safeDaoCall
import ru.dezerom.interdiffer.data.utils.safeVkApiCall
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.mappers.toDomain
import java.lang.Integer.min
import javax.inject.Inject

class VkSocietyRepository @Inject constructor(
    private val usersApiService: UsersApiService,
    private val societiesDao: VkSocietiesDao,
    private val userSocietyRelationsDao: UserSocietyRelationsDao
) {

    suspend fun reloadUserSubscriptions(userId: Int): RequestResult<List<VkSocietyModel>> {
        if (!addUserSocieties(userId)) return RequestResult.Error.Network

        return getSavedSocieties(userId)
    }

    suspend fun getSavedSocieties(userId: Int): RequestResult<List<VkSocietyModel>> {
        return safeDaoCall {
            val relations = userSocietyRelationsDao.getUserRelations(userId)
            val societies = societiesDao.getUserSocieties(userId).map { it.toDomain() }

            societies.sortedBy { society ->
                relations.find { it.societyId == society.id }?.orderNumber ?: Int.MAX_VALUE
            }
        }
    }

    suspend fun addUserSocieties(userId: Int): Boolean {
        val countRes = safeVkApiCall(
            call = {
                usersApiService.getUserSubscriptions(userId = userId)
            },
            onNullValue = { RequestResult.Error.Network },
            successMapper = { it.data?.count }
        )

        var count = if (countRes is RequestResult.Success)
            countRes.data
        else
            return false

        var result: Boolean
        var offset = 0
        val societies = mutableListOf<VkSocietyDataModel>()
        while (count > 0) {
            val societiesRes = safeVkApiCall(
                call = {
                    usersApiService.getUserSubscriptions(
                        userId = userId,
                        offset = offset,
                        count = min(count, MAX_COUNT_PER_BATCH),
                        fields = SOCIETY_FIELDS,
                        extended = true
                    )
                },
                onNullValue = { RequestResult.Error.Network },
                successMapper = { response -> response.data?.societies?.filterNotNull() }
            )

            result = if (societiesRes is RequestResult.Success) {
                societies.addAll(societiesRes.data)
                true
            } else {
                false
            }

            if (!result) break

            count -= MAX_COUNT_PER_BATCH
            offset += MAX_COUNT_PER_BATCH
        }

        result = writeSocietiesToDb(societies) && changeRelations(userId, societies)

        return result
    }

    private suspend fun writeSocietiesToDb(societies: List<VkSocietyDataModel>): Boolean {
        return safeDaoAction {
            societiesDao.saveSocieties(societies)
        }
    }

    private suspend fun changeRelations(userId: Int, societies: List<VkSocietyDataModel>): Boolean {
        return safeDaoAction {
            userSocietyRelationsDao.deleteRelationsByUserId(userId)
        } && safeDaoAction {
            userSocietyRelationsDao.saveUserSocietyRelations(
                relations = societies.mapIndexed { index, society ->
                    UserSocietyRelationDataModel(
                        id = AUTO_GENERATED_ID,
                        userId = userId,
                        societyId = society.id ?: 0,
                        orderNumber = index
                    )
                }
            )
        }
    }

    companion object {
        private const val SOCIETY_FIELDS = "activity,age_limits,description"
        private const val AUTO_GENERATED_ID = 0L
        private const val MAX_COUNT_PER_BATCH = 200
    }
}
