package ru.dezerom.interdiffer.data.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SocietiesResponse(

    @Expose
    @SerializedName("count")
    val count: Int? = null,

    @Expose
    @SerializedName("items")
    val societies: List<SocietiesResponse> = emptyList()
)
