package ru.dezerom.interdiffer.presentation.utils.res.destinations

sealed class NestedNavDestinations(
    val rawRoute: String
) {
    abstract val argName: String

    object VkUserDetails: NestedNavDestinations(
        rawRoute = "vk_user_details"
    ) {
        override val argName: String = "vk_user_details_screen"
    }

    object VkUserPicker: NestedNavDestinations(
        rawRoute = "comparison_creator_screen"
    ) {
        override val argName: String = "comparison_creator_screen_arg"
    }

    object ComparisonDetail : NestedNavDestinations(
        rawRoute = "comparison_detail_screen"
    ) {
        override val argName: String = "comparison_detail_screen_arg"
    }

    fun asRoute(): String {
        val argsString = "/{$argName}"

        return "$rawRoute$argsString"
    }

    fun withArg(arg: Int) =
        "$rawRoute/$arg"

}
