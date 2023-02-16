package ru.dezerom.interdiffer.data.data_base.dao

import androidx.room.*
import ru.dezerom.interdiffer.data.models.VkUserDataModel

@Dao
interface VkUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVkUser(user: VkUserDataModel)

    @Query("SELECT * FROM VkUserDataModel")
    suspend fun getAllVkUsers(): List<VkUserDataModel>?

    @Query("DELETE FROM VkUserDataModel WHERE id = :id")
    suspend fun deleteVkUser(id: Int)

    @Query("SELECT * FROM VkUserDataModel WHERE id = :id")
    suspend fun getUserById(id: Int): VkUserDataModel?

}
