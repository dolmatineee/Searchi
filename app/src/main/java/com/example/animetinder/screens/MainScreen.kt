package com.example.animetinder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.animetinder.MyApplication
import com.example.animetinder.factory.ListsViewModelFactory
import com.example.animetinder.factory.SingleGameViewModelFactory
import com.example.animetinder.navigation.AppNavGraph
import com.example.animetinder.navigation.NavigationItem
import com.example.animetinder.navigation.Screen
import com.example.animetinder.navigation.rememberNavigationState
import com.example.animetinder.ui.theme.BackgroundColor

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    val context = LocalContext.current
    val appDatabase = (context.applicationContext as MyApplication).database
    val likedMovieDao = appDatabase.likedMovieDao()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = BackgroundColor,
                contentColor = BackgroundColor
            ) {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val items = listOf(
                    NavigationItem.SingleGame,
                    NavigationItem.CooperativeGame,
                    NavigationItem.Lists,
                    //NavigationItem.Profile,
                )

                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            val iconRes = if (selected) item.selectedIcon else item.unselectedIcon
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = BackgroundColor
                        )
                    )
                }
            }

        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,

            singleGameContent = {
                SingleGameScreen(
                    paddingValues = paddingValues,
                    onCardClickListener = { movieId ->
                        navigationState.navigateTo("${Screen.MovieDetails.route}/$movieId")
                    },
                    viewModelFactory = SingleGameViewModelFactory(likedMovieDao)
                )
            },
            detailsScreenContent = { movie ->
                MovieDetailsScreen(movie)
            },
            cooperativeGameContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundColor)
                        .padding(paddingValues)
                ) {

                }
            },
            listsContent = {
                ListsScreen(
                    paddingValues = paddingValues,
                    onLikedMovieItemClickListener =  { movieId ->
                    navigationState.navigateTo("${Screen.MovieDetails.route}/$movieId")
                },
                    viewModelFactory = ListsViewModelFactory(likedMovieDao)
                )
            },
            profileContent = {

            },
            searchContent = {

            }
        )

    }
}