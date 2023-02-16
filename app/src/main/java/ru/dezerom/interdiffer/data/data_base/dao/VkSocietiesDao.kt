package ru.dezerom.interdiffer.data.data_base.dao

import androidx.room.*
import ru.dezerom.interdiffer.data.models.VkSocietyDataModel

@Dao
interface VkSocietiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSocieties(societies: List<VkSocietyDataModel>)

    @Query(
        "SELECT * FROM VkSocietyDataModel WHERE id IN" +
            "(SELECT societyId FROM UserSocietyRelationDataModel WHERE userId = :userId)")
    suspend fun getUserSocieties(userId: Int): List<VkSocietyDataModel>

}
