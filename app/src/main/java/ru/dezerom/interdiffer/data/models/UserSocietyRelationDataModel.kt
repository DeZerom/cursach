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
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class UserSocietyRelationDataModel(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(index = true)
    val userId: Int,

    @ColumnInfo(index = true)
    val societyId: Int
)
