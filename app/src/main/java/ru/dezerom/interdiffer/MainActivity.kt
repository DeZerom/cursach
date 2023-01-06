package ru.dezerom.interdiffer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.dezerom.interdiffer.presentation.sreens.comparisons.ComparisonsScreen
import ru.dezerom.interdiffer.presentation.sreens.people.PeopleScreen
import ru.dezerom.interdiffer.presentation.utils.Dimens
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.NavDestinations
import ru.dezerom.interdiffer.ui.theme.InterDifferTheme
import ru.dezerom.interdiffer.ui.theme.Shapes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterDifferTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomBar(navController = navController) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavDestinations.People.route,
                        modifier = Modifier.padding(it))
                    {
                        composable(NavDestinations.People.route) { PeopleScreen() }
                        composable(NavDestinations.Comparisons.route) { ComparisonsScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar() {}

@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.Transparent
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val items = listOf(
            NavDestinations.People,
            NavDestinations.Comparisons
        )

        Card(
            modifier = FullWidthModifier,
            shape = Shapes.small,
            backgroundColor = Color.White,
            elevation = Dimens.Elevations.baseElevation,

        ) {
            Row {
                items.forEach {
                    BottomNavigationItem(
                        selected = it.route == currentDestination?.route,
                        icon = { Image(painter = painterResource(it.icon), contentDescription = null) },
                        label = { Text(text = stringResource(it.title)) },
                        onClick = {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}


