package com.agrohi.kulik.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.agrohi.kulik.R
import com.agrohi.kulik.ui.screens.AddPostScreen
import com.agrohi.kulik.ui.screens.ExploreScreen
import com.agrohi.kulik.ui.screens.FeedScreen
import com.agrohi.kulik.ui.screens.HomeScreen
import com.agrohi.kulik.ui.screens.ProfileScreen
import com.agrohi.kulik.ui.theme.LightGreen

sealed class Screen(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    data object Profile : Screen("profile", Icons.Filled.AccountCircle, R.string.profile)
    data object Home : Screen("home", Icons.Filled.Home, R.string.main)
    data object Explore : Screen("explore", Icons.Filled.Search, R.string.explore)
    data object AddPost : Screen("addpost", Icons.Filled.AddCircle, R.string.post)
    data object Feed : Screen("feed", Icons.Filled.Favorite, R.string.feed)
    data object GoogleSignIn : Screen("googlesignin", Icons.Filled.Favorite, R.string.feed)
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
    )

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                containerColor = LightGreen
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                screen.icon,
                                contentDescription = if (screen.route == "addpost") "add post" else null,
                                modifier = if (screen.route == "addpost") Modifier.size(50.dp) else Modifier
                            )
                        },
                        label = if (screen.route == "addpost") null else {
                            { Text(stringResource(screen.resourceId)) }
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
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
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController = navController) }
            composable(Screen.Explore.route) { ExploreScreen(navController = navController) }
            composable(Screen.Feed.route) { FeedScreen(navController = navController) }
            composable(Screen.AddPost.route) { AddPostScreen(navController = navController) }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToHome = { navController.navigate("home") },
                    navController,
                )
            }
        }
    }
}

object Destinations {
    const val BASICS_START = "Main"
}