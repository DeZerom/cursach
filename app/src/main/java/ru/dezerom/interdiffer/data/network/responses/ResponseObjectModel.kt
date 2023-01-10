package ru.dezerom.interdiffer.data.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.dezerom.interdiffer.data.models.VkErrorDataModel

data class ResponseObjectModel<T>(

    @Expose
    @SerializedName("response")
    val data: T? = null,

    @Expose
    @SerializedName("error")
    override val vkError: VkErrorDataModel? = null

): VkErrorContainer
