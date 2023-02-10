package ru.dezerom.interdiffer.presentation.utils

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable
import ru.dezerom.interdiffer.presentation.utils.res.destinations.RootNavDestinations

fun NavDestination?.isRootDestination(): Boolean {
    this ?: return false

    return route == RootNavDestinations.People.route || route == RootNavDestinations.Comparisons.route
}

fun NavDestination?.toPresentationNavDestination(): RootNavDestinations? =
    RootNavDestinations.fromRoute(this?.route)

fun NavGraphBuilder.composableWithArgs(
    route: String,
    argName: String,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = listOf(navArgument(argName) { type = NavType.IntType }),
        content = content
    )
}
