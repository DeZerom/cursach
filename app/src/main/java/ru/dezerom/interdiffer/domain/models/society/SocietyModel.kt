package ru.dezerom.interdiffer.domain.models.society

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.dezerom.interdiffer.domain.models.DeactivationType

@Parcelize
data class SocietyModel(
    val id: Int,
    val name: String,
    val isClosed: Boolean,
    val deactivationType: DeactivationType,
    val type: SocietyType,
    val photo100: String,
    val photo200: String,
    val activity: String,
    val ageLimits: SocietyAgeLimits,
    val description: String
): Parcelable
