package com.agrohi.kulik

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.agrohi.kulik.ui.theme.LightGreen

sealed class Screen(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    object Profile : Screen("profile", icon = Icons.Filled.AccountCircle, R.string.profile)
    object Home : Screen("home", icon = Icons.Filled.Home, R.string.main)
    object Explore : Screen("explore", icon = Icons.Filled.Search, R.string.explore)
    object AddPost : Screen("addpost", icon = Icons.Filled.AddCircle, R.string.post)
    object Feed : Screen("feed", icon = Icons.Filled.Favorite, R.string.feed)
    object GoogleSignIn : Screen("googlesignin", icon = Icons.Filled.Favorite, R.string.feed)
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
) {

    val items = listOf(
        Screen.Home,
        Screen.Explore,
        Screen.AddPost,
        Screen.Feed,
        Screen.Profile,
//        Screen.GoogleSignIn,
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
                        icon = {
                            if (!listOf("googlesignin").contains(screen.route)) {
                                if (screen.route == "addpost") {
                                    Icon(
                                        screen.icon,
                                        contentDescription = "add post",
                                        modifier = Modifier.size(50.dp)
                                    )
                                } else
                                    Icon(screen.icon, contentDescription = null)
                            }
                        }, // add conditional icon size
                        label = {
                            if (screen.route == "addpost") {
                                // no string
                        } else Text(stringResource(screen.resourceId))
                                },
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
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { Home() }
            composable(Screen.Explore.route) { Explore() }
            composable(Screen.Feed.route) { Feed() }
            composable(Screen.AddPost.route) { AddPostScreen() }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToHome = { navController.navigate("home") },
                    navController,
                    /*...*/
                )
            }

        }
    }
}

@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit,
    navController: NavHostController,
    /*...*/
) {
    /*...*/
    Profile(onNavigateToHome, navController)
}

object Destinations {
    const val BASICS_START = "Main"
}