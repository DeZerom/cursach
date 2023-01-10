package ru.dezerom.interdiffer.data.network.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserRequest(

    @Expose
    @SerializedName("user_ids")
    val ids: List<Int>,

    @Expose
    @SerializedName("fields")
    val fields: List<String> = emptyList(),

    @Expose
    @SerializedName("name_case")
    val nameCase: String
)
