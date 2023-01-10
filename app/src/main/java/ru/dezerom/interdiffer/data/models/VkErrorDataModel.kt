package ru.dezerom.interdiffer.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VkErrorDataModel(

    @Expose
    @SerializedName("error_code")
    val errorCode: Int? = null
)
