package com.example.animetinder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.animetinder.data.Movie


@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    singleGameContent: @Composable () -> Unit,
    detailsScreenContent: @Composable (Movie) -> Unit,
    cooperativeGameContent: @Composable () -> Unit,
    searchContent: @Composable () -> Unit,
    listsContent: @Composable () -> Unit,
    profileContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.SingleGame.route
    ) {

        singleGameNavGraph(
            singleGameScreenContent = singleGameContent,
            detailsMovieScreenContent = detailsScreenContent
        )

        composable(Screen.CooperativeGame.route) {
            cooperativeGameContent()
        }

        composable(Screen.Search.route) {
            searchContent()
        }

        composable(Screen.Lists.route) {
            listsContent()
        }

        composable(Screen.Profile.route) {
            profileContent()
        }


    }
}