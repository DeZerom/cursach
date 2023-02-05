package ru.dezerom.interdiffer.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class VkUserDataModel(

    @Expose
    @SerializedName("id")
    @PrimaryKey
    val id: Int? = null,

    @Expose
    @SerializedName("first_name")
    val firstName: String? = null,

    @Expose
    @SerializedName("last_name")
    val lastName: String? = null,

    @Expose
    @SerializedName("is_closed")
    val isClosed: Boolean? = null,

    @Expose
    @SerializedName("deactivated")
    val deactivated: String? = null,

    @Expose
    @SerializedName("bdate")
    val birthDate: String? = null,

    @Expose
    @SerializedName("photo_100")
    val photo100: String? = null,

    @Expose
    @SerializedName("photo_200")
    val photo200: String? = null
)
