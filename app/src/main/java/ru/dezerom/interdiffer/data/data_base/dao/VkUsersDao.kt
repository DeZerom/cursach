package ru.dezerom.interdiffer.data.data_base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.dezerom.interdiffer.data.models.VkUserDataModel

@Dao
interface VkUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVkUser(user: VkUserDataModel)

    @Query("SELECT * FROM VkUserDataModel")
    suspend fun getAllVkUsers(): List<VkUserDataModel>?

}
