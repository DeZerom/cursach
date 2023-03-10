package ru.dezerom.interdiffer.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = VkUserDataModel::class,
            parentColumns = ["id"],
            childColumns = ["firstPersonId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = VkUserDataModel::class,
            parentColumns = ["id"],
            childColumns = ["secondPersonId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ComparisonDataModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val firstPersonId: Int,

    val secondPersonId: Int

)
