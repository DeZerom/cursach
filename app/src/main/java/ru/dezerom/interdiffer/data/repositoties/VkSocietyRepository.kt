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
            societiesDao.getUserSocieties(userId).map { it.toDomain() }
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
        val societies = mutableListOf<VkSocietyDataModel>()
        while (count > 0) {
            val societiesRes = safeVkApiCall(
                call = {
                    usersApiService.getUserSubscriptions(
                        userId = userId,
                        offset = 0,
                        count = min(count, MAX_COUNT_PER_BATCH),
                        fields = SOCIETY_FIELDS,
                        extended = true
                    )
                },
                onNullValue = { RequestResult.Error.Network },
                successMapper = { response -> response.data?.societies }
            )

            result = if (societiesRes is RequestResult.Success) {
                societies.addAll(societiesRes.data)
            } else {
                false
            }

            if (!result) break

            count -= MAX_COUNT_PER_BATCH
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
        val relations = safeDaoCall(
            daoCall = { userSocietyRelationsDao.getRelationsByUserId(userId) },
            onNullValue = { RequestResult.Success(emptyList()) }
        )

        if (relations !is RequestResult.Success) return false

        return safeDaoAction {
            userSocietyRelationsDao.deleteUserSocietyRelations(
                findRelationsToDelete(relations.data, societies)
            )
        } && safeDaoAction {
            userSocietyRelationsDao.saveUserSocietyRelations(
                createRelationsToAdd(relations.data, societies, userId)
            )
        }
    }

    private fun findRelationsToDelete(
        relations: List<UserSocietyRelationDataModel>,
        societies: List<VkSocietyDataModel>
    ): List<UserSocietyRelationDataModel> {
        return relations.filter { relation ->
            !societies.any { it.id == relation.societyId }
        }
    }

    private fun createRelationsToAdd(
        relations: List<UserSocietyRelationDataModel>,
        societies: List<VkSocietyDataModel>,
        userId: Int
    ): List<UserSocietyRelationDataModel> {
        val needRelation = societies.filter { society ->
            !relations.any { it.societyId == society.id }
        }

        return needRelation.map {
            UserSocietyRelationDataModel(
                id = AUTO_GENERATED_ID,
                userId = userId,
                societyId = it.id ?: 0
            )
        }
    }

    companion object {
        private const val SOCIETY_FIELDS = "activity,age_limits,description"
        private const val AUTO_GENERATED_ID = 0L
        private const val MAX_COUNT_PER_BATCH = 200
    }
}
