package com.agrohi.kulik

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.agrohi.kulik.ui.theme.LightGreen

sealed class Screen(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    object Profile : Screen("profile", icon = Icons.Filled.AccountCircle, R.string.profile)
    object Main : Screen("main", icon = Icons.Filled.Home, R.string.main)
    object Feed : Screen("feed", icon = Icons.Filled.Favorite, R.string.feed)
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {

    val items = listOf(
        Screen.Main,
        Screen.Feed,
        Screen.Profile,
    )

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                backgroundColor = LightGreen
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Main.route, Modifier.padding(innerPadding)) {
            composable(Screen.Main.route) { Home()  }
            composable(Screen.Feed.route) { Feed() }
            composable(Screen.Profile.route) { ProfileScreen(
                onNavigateToAbout = { navController.navigate("About") },
                /*...*/
            ) }

        }
    }
}

@Composable
fun ProfileScreen(
    onNavigateToAbout: () -> Unit,
    /*...*/
) {
    /*...*/
    Profile()
}

object Destinations {
    const val BASICS_START = "Main"
}