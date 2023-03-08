package ru.dezerom.interdiffer.data.data_base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.dezerom.interdiffer.data.models.ComparisonDataModel

@Dao
interface ComparisonDao {

    @Insert
    suspend fun insertComparison(comparison: ComparisonDataModel)

    @Query("SELECT * FROM ComparisonDataModel")
    suspend fun getAll(): List<ComparisonDataModel>?

    @Query("SELECT * FROM ComparisonDataModel WHERE id = :id")
    suspend fun getById(id: Int): ComparisonDataModel

    @Query("DELETE FROM ComparisonDataModel WHERE id = :id")
    suspend fun deleteById(id: Int)

}
