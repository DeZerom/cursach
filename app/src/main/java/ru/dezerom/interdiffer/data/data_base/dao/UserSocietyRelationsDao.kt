package ru.dezerom.interdiffer.data.data_base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.dezerom.interdiffer.data.models.UserSocietyRelationDataModel

@Dao
interface UserSocietyRelationsDao {

    @Insert
    suspend fun saveUserSocietyRelations(relations: List<UserSocietyRelationDataModel>)

    @Query("DELETE FROM UserSocietyRelationDataModel WHERE userId = :userId")
    suspend fun deleteRelationsByUserId(userId: Int)

}
