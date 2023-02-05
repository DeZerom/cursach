package ru.dezerom.interdiffer.presentation.utils.res.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.dezerom.interdiffer.presentation.sreens.comparisons.ComparisonsScreen
import ru.dezerom.interdiffer.presentation.sreens.people.PeopleScreen
import ru.dezerom.interdiffer.presentation.utils.res.destinations.Graphs
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
        peopleGraph()
        comparisonsGraph()
    }
}

fun NavGraphBuilder.peopleGraph() {
    navigation(RootNavDestinations.People.route, Graphs.PeopleGraph.route) {
        composable(RootNavDestinations.People.route) {
            PeopleScreen(hiltViewModel())
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
