package ru.dezerom.interdiffer.presentation.utils

import androidx.navigation.NavDestination
import ru.dezerom.interdiffer.presentation.utils.res.destinations.RootNavDestinations

fun NavDestination?.isRootDestination(): Boolean {
    this ?: return false

    return route == RootNavDestinations.People.route || route == RootNavDestinations.Comparisons.route
}

fun NavDestination?.toPresentationNavDestination(): RootNavDestinations? =
    RootNavDestinations.fromRoute(this?.route)
