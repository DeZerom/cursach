package ru.dezerom.interdiffer.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import ru.dezerom.interdiffer.data.type_converters.SocietyTypeAdapter

@Entity
@JsonAdapter(SocietyTypeAdapter::class)
data class VkSocietyDataModel(

    @Expose
    @SerializedName(SocietyTypeAdapter.ID)
    @PrimaryKey
    val id: Int? = null,

    @Expose
    @SerializedName(SocietyTypeAdapter.NAME)
    val name: String? = null,

    @Expose
    @SerializedName(SocietyTypeAdapter.IS_CLOSED)
    val isClosed: Int? = null,

    @Expose
    @SerializedName(SocietyTypeAdapter.DEACTIVATED)
    val deactivated: String? = null,

    @Expose
    @SerializedName(SocietyTypeAdapter.TYPE)
    val type: String? = null,

    @Expose
    @SerializedName(SocietyTypeAdapter.PHOTO_100)
    val photo100: String? = null,

    @Expose
    @SerializedName(SocietyTypeAdapter.PHOTO_200)
    val photo200: String? = null,

    @Expose
    @SerializedName(SocietyTypeAdapter.ACTIVITY)
    val activity: String? = null,

    @Expose
    @SerializedName(SocietyTypeAdapter.AGE_LIMITS)
    val ageLimits: Int? = null,

    @Expose
    @SerializedName(SocietyTypeAdapter.DESCRIPTION)
    val description: String? = null
)
