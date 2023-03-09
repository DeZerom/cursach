package ru.dezerom.interdiffer.data.repositoties

import ru.dezerom.interdiffer.data.data_base.dao.ComparisonDao
import ru.dezerom.interdiffer.data.data_base.dao.VkUsersDao
import ru.dezerom.interdiffer.data.models.ComparisonDataModel
import ru.dezerom.interdiffer.data.utils.safeDaoAction
import ru.dezerom.interdiffer.data.utils.safeDaoCall
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.mappers.toDomain
import javax.inject.Inject

class ComparisonsRepository @Inject constructor(
    private val comparisonDao: ComparisonDao,
    private val vkUsersDao: VkUsersDao
) {

    suspend fun getById(id: Int): RequestResult<ComparisonModel> {
        return safeDaoCall { mapToDomain(comparisonDao.getById(id)) }
    }

    suspend fun getByUsersId(
        firstUserId: Int,
        secondUserId: Int
    ): RequestResult<ComparisonModel> {
        return safeDaoCall {
            mapToDomain(
                comparisonDao.getByUsersId(
                    firstUserId = firstUserId,
                    secondUserId = secondUserId
                )
            )
        }
    }

    suspend fun deleteComparison(id: Int): Boolean {
        return safeDaoAction {
            comparisonDao.deleteById(id)
        }
    }

    suspend fun createComparison(
        firstUserId: Int,
        secondUserId: Int
    ): Boolean {
        return safeDaoAction {
            comparisonDao.insertComparison(
                ComparisonDataModel(
                    id = 0,
                    firstPersonId = firstUserId,
                    secondPersonId = secondUserId
                )
            )
        }
    }

    suspend fun getSavedComparisons(): RequestResult<List<ComparisonModel>> {
        return safeDaoCall(
            daoCall = { comparisonDao.getAll()?.mapNotNull { mapToDomain(it) } },
            onNullValue = { RequestResult.Success(emptyList()) }
        )
    }

    private suspend fun mapToDomain(model: ComparisonDataModel?): ComparisonModel? {
        if (model == null) return null

        val firstUser = safeDaoCall { vkUsersDao.getUserById(model.firstPersonId)?.toDomain() }
        val secondUser = safeDaoCall { vkUsersDao.getUserById(model.secondPersonId)?.toDomain() }

        return if (firstUser is RequestResult.Success && secondUser is RequestResult.Success) {
            ComparisonModel(
                id = model.id,
                firstPerson = firstUser.data,
                secondPerson = secondUser.data
            )
        } else {
            null
        }
    }

}
