package ru.dezerom.interdiffer.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = VkUserDataModel::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = VkSocietyDataModel::class,
            parentColumns = ["id"],
            childColumns = ["societyId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
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
