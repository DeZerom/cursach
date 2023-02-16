package ru.dezerom.interdiffer.data.network.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SocietiesRequest(

    @Expose
    @SerializedName("user_id")
    val userId: Int,

    @Expose
    @SerializedName("offset")
    val offset: Int? = null,

    @Expose
    @SerializedName("count")
    val count: Int? = null,

    @Expose
    @SerializedName("fields")
    val fields: String? = null,

    @Expose
    @SerializedName("extended")
    val extended: Boolean = true
)
