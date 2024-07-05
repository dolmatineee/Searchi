package com.example.animetinder.data

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
data class Movie(
    val id: Int,
    val name: String,
    val year: Int,
    val rating: Rating,
    val poster: Poster,
    val countries: List<Countries>,
    val ageRating: String,
    val description: String,
    val genres: List<Genres>,
    val movieLength: Int,
    val persons: List<Persons>,
    val watchability: Watchability
) : Parcelable {
    companion object {

        val MovieType : NavType<Movie> = object : NavType<Movie>(false) {
            override fun get(bundle: Bundle, key: String): Movie? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): Movie {
                return Json.decodeFromString(Movie.serializer(), value)
            }

            override fun put(bundle: Bundle, key: String, value: Movie) {
                bundle.putParcelable(key, value)
            }
        }
    }
}

@Parcelize
@Serializable
data class Rating(
    val imdb: Double
) : Parcelable

@Parcelize
@Serializable
data class Poster(
    val url: String
) : Parcelable

@Parcelize
@Serializable
data class Countries(
    val name: String
) : Parcelable

@Parcelize
@Serializable
data class Genres(
    val name: String
) : Parcelable

@Parcelize
@Serializable
data class Persons(
    val name: String,
    val profession: String,
    val photo: String
) : Parcelable

@Parcelize
@Serializable
data class Watchability(
    val items: List<WatchabilityItem>
) : Parcelable

@Parcelize
@Serializable
data class WatchabilityItem(
    val name: String,
    val logo: WatchabilityLogo,
    val url: String
) : Parcelable

@Parcelize
@Serializable
data class WatchabilityLogo(
    val url: String
) : Parcelable

