package ru.dezerom.interdiffer.domain.interactors

import ru.dezerom.interdiffer.data.repositoties.ComparisonsRepository
import ru.dezerom.interdiffer.data.repositoties.VkUsersRepository
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import javax.inject.Inject


class ComparisonsInteractor @Inject constructor(
    private val comparisonsRepository: ComparisonsRepository,
    private val vkUsersRepository: VkUsersRepository
) {

    suspend fun deleteComparison(id: Int): Boolean {
        return comparisonsRepository.deleteComparison(id)
    }

    suspend fun createComparison(firstUserId: Int, secondUserId: Int): Boolean {
        return comparisonsRepository.createComparison(firstUserId, secondUserId)
    }

    suspend fun getSavedComparisons(): RequestResult<List<ComparisonModel>> {
        return comparisonsRepository.getSavedComparison()
    }

    suspend fun refreshComparison(comparison: ComparisonModel): RequestResult<ComparisonModel> {
        val reloadSuccess = vkUsersRepository.run {
            reloadUserInfoById(comparison.firstPerson.id) is RequestResult.Success &&
            reloadUserInfoById(comparison.secondPerson.id) is RequestResult.Success
        }

        return if (reloadSuccess) {
            comparisonsRepository.getById(comparison.id)
        } else {
            RequestResult.Error.Network
        }
    }

}
