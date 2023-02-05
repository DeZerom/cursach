package ru.dezerom.interdiffer.presentation.utils.res.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.dezerom.interdiffer.presentation.sreens.comparisons.ComparisonsScreen
import ru.dezerom.interdiffer.presentation.sreens.people.PeopleScreen
import ru.dezerom.interdiffer.presentation.sreens.user_details.UserDetailsScreen
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
        comparisonsGraph()
    }
}

fun NavGraphBuilder.peopleGraph(navController: NavController) {
    navigation(RootNavDestinations.People.route, Graphs.PeopleGraph.route) {
        composable(RootNavDestinations.People.route) {
            PeopleScreen(hiltViewModel(), navController)
        }

        composable(
            route = NestedNavDestinations.VkUserDetails.asRoute(),
            arguments = listOf(
                navArgument(NestedNavDestinations.VkUserDetails.ID) { type = NavType.StringType }
            )
        ) {
            UserDetailsScreen(viewModel = hiltViewModel())
        }
    }
}

fun NavGraphBuilder.comparisonsGraph() {
    navigation(RootNavDestinations.Comparisons.route, Graphs.ComparisonsGraph.route) {
        composable(RootNavDestinations.Comparisons.route) {
            ComparisonsScreen()
        }
    }
}
