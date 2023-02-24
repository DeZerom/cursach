package ru.dezerom.interdiffer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.utils.res.destinations.RootNavDestinations
import ru.dezerom.interdiffer.presentation.utils.res.navhost.MainNavHost
import ru.dezerom.interdiffer.presentation.widgets.BaseCenteredText
import ru.dezerom.interdiffer.ui.theme.InterDifferTheme
import ru.dezerom.interdiffer.ui.theme.Orange
import ru.dezerom.interdiffer.ui.theme.topRoundedShape

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterDifferTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomBar(navController = navController) }
                ) {
                    MainNavHost(navController = navController, modifier = Modifier.padding(it))
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.Transparent
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val items = listOf(
            RootNavDestinations.People,
            RootNavDestinations.Comparisons
        )

        Card(
            modifier = FullWidthModifier,
            shape = topRoundedShape(),
            backgroundColor = Color.White,
            elevation = Dimens.Elevations.baseElevation,

        ) {
            Row {
                items.forEach {
                    val isSelected = currentDestination?.hierarchy?.any {navDestination ->
                        navDestination.route == it.route
                    } == true

                    BottomNavigationItem(
                        selected = isSelected,
                        icon = { Image(painter = painterResource(it.icon), contentDescription = null) },
                        label = {
                            BaseCenteredText(
                                text = stringResource(id = it.title),
                                textColor = if (isSelected) Orange else Color.Black
                            )
                        },
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


