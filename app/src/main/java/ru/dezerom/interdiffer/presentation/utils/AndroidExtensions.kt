package ru.dezerom.interdiffer.presentation.utils

import androidx.navigation.NavDestination

fun NavDestination?.isRootDestination(): Boolean {
    this ?: return false

    return route == NavDestinations.People.route || route == NavDestinations.Comparisons.route
}

fun NavDestination?.toPresentationNavDestination(): NavDestinations? =
    NavDestinations.fromRoute(this?.route)
