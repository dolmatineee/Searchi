package com.example.animetinder.navigation

import android.net.Uri
import com.example.animetinder.data.Movie
import com.google.gson.Gson

sealed class Screen(
    val route: String
) {

    object SingleGame: Screen(ROUTE_SINGLE_GAME)
    object CardsMovie: Screen(ROUTE_CARDS_MOVIE)
    object CooperativeGame: Screen(ROUTE_COOPERATIVE_GAME)
    object Search: Screen(ROUTE_SEARCH)
    object Lists: Screen(ROUTE_LISTS)
    object Profile: Screen(ROUTE_PROFILE)

    object MovieDetails: Screen(ROUTE_MOVIE_DETAILS){

        private const val ROUTE_FOR_ARGS = "details"

        fun getRouteWithArgs(movie: Movie): String {
            val movieJson = Gson().toJson(movie)
            return "${ROUTE_MOVIE_DETAILS}?${KEY_MOVIE}=${Uri.encode(movieJson)}"
        }
    }

    companion object {

        const val KEY_MOVIE = "movie"

        const val ROUTE_SINGLE_GAME = "single_game"
        const val ROUTE_CARDS_MOVIE = "cards_movie"
        const val ROUTE_COOPERATIVE_GAME = "cooperative_game"
        const val ROUTE_SEARCH = "search"
        const val ROUTE_LISTS = "lists"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_MOVIE_DETAILS= "movie_details/{$KEY_MOVIE}"
    }
}

fun String.encode(): String{
    return Uri.encode(this)
}