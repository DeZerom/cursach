package ru.dezerom.interdiffer.data.network.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserRequest(

    @Expose
    @SerializedName("user_ids")
    val screenNames: List<String>,

    @Expose
    @SerializedName("fields")
    val fields: String = "",

    @Expose
    @SerializedName("name_case")
    val nameCase: String = ""
)
