package ru.dezerom.interdiffer.presentation.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.dezerom.interdiffer.R

sealed class NavDestinations(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val title: Int
) {
    object People: NavDestinations("people_screen", R.drawable.ic_people, R.string.people)
    object Comparisons: NavDestinations("comp_Screen", R.drawable.ic_comparasing, R.string.comparisons)

    companion object {
        fun fromRoute(route: String?) = when (route) {
            People.route -> People
            Comparisons.route -> Comparisons
            else -> null
        }
    }
}
