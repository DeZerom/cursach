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

    fun asRoute(): String {
        val argsString = "/{$argName}"

        return "$rawRoute$argsString"
    }

    fun withArg(arg: Int) =
        "$rawRoute/$arg"

}
