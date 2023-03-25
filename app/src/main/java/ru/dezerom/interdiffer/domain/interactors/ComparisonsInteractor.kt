package ru.dezerom.interdiffer.domain.interactors

import ru.dezerom.interdiffer.data.repositoties.ComparisonsRepository
import ru.dezerom.interdiffer.data.repositoties.VkSocietyRepository
import ru.dezerom.interdiffer.data.repositoties.VkUsersRepository
import ru.dezerom.interdiffer.domain.logic.differ.createDetailedComparison
import ru.dezerom.interdiffer.domain.models.comparasion.ComparisonModel
import ru.dezerom.interdiffer.domain.models.comparasion.DetailedComparisonModel
import ru.dezerom.interdiffer.domain.models.society.VkSocietyModel
import ru.dezerom.interdiffer.domain.models.utils.RequestResult
import ru.dezerom.interdiffer.domain.models.utils.castError
import javax.inject.Inject


class ComparisonsInteractor @Inject constructor(
    private val comparisonsRepository: ComparisonsRepository,
    private val vkUsersRepository: VkUsersRepository,
    private val societyRepository: VkSocietyRepository
) {

    suspend fun deleteComparison(id: Int): Boolean {
        return comparisonsRepository.deleteComparison(id)
    }

    suspend fun createComparison(
        firstUserId: Int,
        secondUserId: Int
    ): RequestResult<ComparisonModel> {
        val creationResult = comparisonsRepository.createComparison(firstUserId, secondUserId)

        if (!creationResult) return RequestResult.Error.RoomError

        return comparisonsRepository.getByUsersId(firstUserId, secondUserId)
    }

    suspend fun getSavedComparisons(): RequestResult<List<ComparisonModel>> {
        return comparisonsRepository.getSavedComparisons()
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

    suspend fun getComparisonDetailInfo(comparisonId: Int): RequestResult<DetailedComparisonModel> {
        val comparisonRequest = comparisonsRepository.getById(comparisonId)

        if (comparisonRequest !is RequestResult.Success) {
            return comparisonRequest.castError()
        }

        val societiesFirst = getUserSocieties(comparisonRequest.data.firstPerson.id)
        val societiesSecond = getUserSocieties(comparisonRequest.data.secondPerson.id)

        return RequestResult.Success(
            createDetailedComparison(
                comparison = comparisonRequest.data,
                firstPersonSocieties = societiesFirst,
                secondPersonSocieties = societiesSecond
            )
        )
    }

    private suspend fun getUserSocieties(userId: Int): List<VkSocietyModel> {
        return when (val requestResult = societyRepository.getSavedSocieties(userId)) {
            is RequestResult.Success -> requestResult.data

            else -> emptyList()
        }
    }

}
