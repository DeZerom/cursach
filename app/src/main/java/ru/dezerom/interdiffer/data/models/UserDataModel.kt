package ru.dezerom.interdiffer.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDataModel(

    @Expose
    @SerializedName("id")
    val id: Int? = null,

    @Expose
    @SerializedName("first_name")
    val firstName: String? = null,

    @Expose
    @SerializedName("last_name")
    val lastName: String? = null,

    @Expose
    @SerializedName("is_closed")
    val isClosed: Boolean? = null
)
