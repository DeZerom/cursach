package ru.dezerom.interdiffer.data.network.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SocietiesRequest(

    @Expose
    @SerializedName("user_id")
    val userId: Int,

    @Expose
    @SerializedName("offset")
    val offset: Int,

    @Expose
    @SerializedName("count")
    val count: Int,

    @Expose
    @SerializedName("fields")
    val fields: List<String>,

    @Expose
    @SerializedName("extended")
    val extended: Boolean = true
)
