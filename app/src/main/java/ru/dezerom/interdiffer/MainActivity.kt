package ru.dezerom.interdiffer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.dezerom.interdiffer.presentation.sreens.comparisons.ComparisonsScreen
import ru.dezerom.interdiffer.presentation.sreens.people.PeopleScreen
import ru.dezerom.interdiffer.presentation.utils.*
import ru.dezerom.interdiffer.presentation.widgets.BaseCenteredText
import ru.dezerom.interdiffer.presentation.widgets.BoldExtraBigText
import ru.dezerom.interdiffer.ui.theme.InterDifferTheme
import ru.dezerom.interdiffer.ui.theme.Orange
import ru.dezerom.interdiffer.ui.theme.bottomRoundedShape
import ru.dezerom.interdiffer.ui.theme.topRoundedShape

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterDifferTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = { TopBar(navController = navController) },
                    bottomBar = { BottomBar(navController = navController) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavDestinations.People.route,
                        modifier = Modifier.padding(it))
                    {
                        composable(NavDestinations.People.route) { PeopleScreen(hiltViewModel()) }
                        composable(NavDestinations.Comparisons.route) { ComparisonsScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Card(
            shape = bottomRoundedShape(),
            backgroundColor = Color.White,
            elevation = Dimens.Elevations.baseElevation,
            modifier = MaxSizeModifier
        ) {
            Box(
                modifier = FullWidthModifier
            ) {
                if (!currentDestination.isRootDestination()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier.align(Alignment.CenterStart)
                            .clickable { navController.popBackStack() }
                    )
                }

                currentDestination.toPresentationNavDestination()?.let {
                    BoldExtraBigText(
                        text = stringResource(it.title),
                        Modifier.align(Alignment.Center)
                    )
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
            NavDestinations.People,
            NavDestinations.Comparisons
        )

        Card(
            modifier = FullWidthModifier,
            shape = topRoundedShape(),
            backgroundColor = Color.White,
            elevation = Dimens.Elevations.baseElevation,

        ) {
            Row {
                items.forEach {
                    val isSelected = it.route == currentDestination?.route

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


