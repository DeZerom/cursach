package ru.dezerom.interdiffer.presentation.utils.res.destinations

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.dezerom.interdiffer.R

sealed class RootNavDestinations(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val title: Int
) {
    object People: RootNavDestinations("people_screen", R.drawable.ic_people, R.string.people)
    object Comparisons: RootNavDestinations("comp_Screen", R.drawable.ic_comparasing, R.string.comparisons)

    companion object {
        fun fromRoute(route: String?) = when (route) {
            People.route -> People
            Comparisons.route -> Comparisons
            else -> null
        }
    }
}
