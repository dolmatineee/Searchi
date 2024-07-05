package com.example.animetinder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.animetinder.data.Movie
import com.example.animetinder.viewmodels.MovieDetailsViewModel

fun NavGraphBuilder.singleGameNavGraph(
    singleGameScreenContent: @Composable () -> Unit,
    detailsMovieScreenContent: @Composable (Movie) -> Unit,
) {
    navigation(
        startDestination = Screen.CardsMovie.route,
        route = Screen.SingleGame.route
    ) {
        composable(Screen.CardsMovie.route) {
            singleGameScreenContent()
        }
        composable(
            route = Screen.MovieDetails.route + "/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            val viewModel: MovieDetailsViewModel = viewModel()
            LaunchedEffect(movieId) {
                viewModel.getMovieById(movieId)
            }
            val movie by viewModel.movie.observeAsState()
            movie?.let {
                detailsMovieScreenContent(it)
            }
        }
    }
}