package com.example.animetinder.repository

import com.example.animetinder.data.Movie
import com.example.animetinder.data.LikedMovie

interface MovieRepository {
    suspend fun insertLikedMovie(movieId: Int)
    suspend fun getAllLikedMovies(): List<LikedMovie>
    suspend fun getMovieById(id: Int): Movie
}