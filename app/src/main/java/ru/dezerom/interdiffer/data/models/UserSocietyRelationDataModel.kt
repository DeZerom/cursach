package ru.dezerom.interdiffer.data.models

import androidx.room.*

@Entity
data class UserSocietyRelationDataModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long,

    @ColumnInfo(name = "userId", index = true)
    val userId: Int,

    @ColumnInfo(name = "societyId", index = true)
    val societyId: Int,

    @ColumnInfo("orderNumber")
    val orderNumber: Int
)
