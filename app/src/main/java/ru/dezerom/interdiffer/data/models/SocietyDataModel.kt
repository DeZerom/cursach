package ru.dezerom.interdiffer.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SocietyDataModel(

    @Expose
    @SerializedName("id")
    val id: Int? = null,

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