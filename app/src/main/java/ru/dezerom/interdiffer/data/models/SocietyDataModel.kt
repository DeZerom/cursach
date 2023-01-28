package ru.dezerom.interdiffer.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = VkUserDataModel::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["userId"],
            orders = [Index.Order.ASC],
            name = "user_society_index"
        )
    ]
)
data class SocietyDataModel(

    @Expose
    @SerializedName("id")
    @PrimaryKey
    val id: Int? = null,

    val userId: Int? = null,

    @Expose
    @SerializedName("name")
    val name: String? = null,

    @Expose
    @SerializedName("is_closed")
    val isClosed: Boolean? = null,

    @Expose
    @SerializedName("deactivated")
    val deactivated: String? = null,

    @Expose
    @SerializedName("type")
    val type: String? = null,

    @Expose
    @SerializedName("photo_100")
    val photo100: String? = null,

    @Expose
    @SerializedName("photo_200")
    val photo200: String? = null,

    @Expose
    @SerializedName("activity")
    val activity: String? = null,

    @Expose
    @SerializedName("age_limits")
    val ageLimits: Int? = null,

    @Expose
    @SerializedName("description")
    val description: String? = null
)
