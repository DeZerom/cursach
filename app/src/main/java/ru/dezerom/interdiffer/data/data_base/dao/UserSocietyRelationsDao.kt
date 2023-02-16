package ru.dezerom.interdiffer.data.data_base.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.dezerom.interdiffer.data.models.UserSocietyRelationDataModel

@Dao
interface UserSocietyRelationsDao {

    @Insert
    suspend fun saveUserSocietyRelations(relations: List<UserSocietyRelationDataModel>)

    @Delete
    suspend fun deleteUserSocietyRelations(relations: List<UserSocietyRelationDataModel>)

    @Query("SELECT * FROM UserSocietyRelationDataModel WHERE userId = :userId")
    suspend fun getRelationsByUserId(userId: Int): List<UserSocietyRelationDataModel>?

    @Query("DELETE FROM UserSocietyRelationDataModel WHERE userId = :userId")
    suspend fun deleteRelationsByUserId(userId: Int)

}
