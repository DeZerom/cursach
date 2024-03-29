package ru.dezerom.interdiffer.presentation.utils.res.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.dezerom.interdiffer.presentation.sreens.comparison_detail.pager.ComparisonDetailScreen
import ru.dezerom.interdiffer.presentation.sreens.comparisons.ComparisonsScreen
import ru.dezerom.interdiffer.presentation.sreens.people.PeopleScreen
import ru.dezerom.interdiffer.presentation.sreens.user_details.UserDetailsScreen
import ru.dezerom.interdiffer.presentation.sreens.user_picker.UserPickerScreen
import ru.dezerom.interdiffer.presentation.utils.composableWithArgs
import ru.dezerom.interdiffer.presentation.utils.res.destinations.Graphs
import ru.dezerom.interdiffer.presentation.utils.res.destinations.NestedNavDestinations
import ru.dezerom.interdiffer.presentation.utils.res.destinations.RootNavDestinations

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Graphs.PeopleGraph.route,
        modifier = modifier,
    ) {
        peopleGraph(navController)
        comparisonsGraph(navController)
    }
}

fun NavGraphBuilder.peopleGraph(navController: NavController) {
    navigation(RootNavDestinations.People.route, Graphs.PeopleGraph.route) {
        composable(RootNavDestinations.People.route) {
            PeopleScreen(hiltViewModel(), navController)
        }

        composableWithArgs(
            route = NestedNavDestinations.VkUserDetails.asRoute(),
            argName = NestedNavDestinations.VkUserDetails.argName
        ) {
            UserDetailsScreen(viewModel = hiltViewModel(), navController)
        }
    }
}

fun NavGraphBuilder.comparisonsGraph(navController: NavController) {
    navigation(RootNavDestinations.Comparisons.route, Graphs.ComparisonsGraph.route) {
        composable(RootNavDestinations.Comparisons.route) {
            ComparisonsScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(NestedNavDestinations.VkUserPicker.asRoute()) {
            UserPickerScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composableWithArgs(
            route = NestedNavDestinations.ComparisonDetail.asRoute(),
            argName = NestedNavDestinations.ComparisonDetail.argName
        ) {
            ComparisonDetailScreen(viewModel = hiltViewModel(), navController = navController)
        }
    }
}
