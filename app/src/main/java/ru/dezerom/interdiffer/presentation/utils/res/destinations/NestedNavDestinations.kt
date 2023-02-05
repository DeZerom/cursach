package ru.dezerom.interdiffer.presentation.utils.res.destinations

sealed class NestedNavDestinations(
    val rawRoute: String,
    val argName: String
) {

    object VkUserDetails: NestedNavDestinations(
        rawRoute = "vk_user_details",
        argName = "id"
    ) {
        const val ID = "id"
    }

    fun asRoute(): String {
        val argsString = "/{$argName}"

        return "$rawRoute$argsString"
    }

    fun withArg(arg: String) =
        "$rawRoute/{$arg}"

}
