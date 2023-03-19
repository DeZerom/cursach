package ru.dezerom.interdiffer.domain.models.comparasion

import androidx.annotation.StringRes
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.user.VkUserModel

sealed class ComparisonInvalidationReason {

    @get:StringRes
    abstract val descriptionStrRes: Int

    abstract val formatArgs: Array<String>

    class TooFewSocieties(
        firstUser: VkUserModel,
        secondUser: VkUserModel? = null
    ): ComparisonInvalidationReason() {
        override val descriptionStrRes: Int
        override val formatArgs: Array<String>

        init {
            if (secondUser != null) {
                formatArgs = arrayOf(firstUser.fullName, secondUser.fullName)
                descriptionStrRes = R.string.invalid_comparison_too_few_societies_two_users
            } else {
                formatArgs = arrayOf(firstUser.fullName)
                descriptionStrRes = R.string.invalid_comparison_too_few_societies_single_user
            }
        }
    }

}
