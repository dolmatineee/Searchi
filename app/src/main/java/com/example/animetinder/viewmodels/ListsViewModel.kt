package com.example.animetinder.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animetinder.data.Movie
import com.example.animetinder.data.LikedMovie
import com.example.animetinder.data.LikedMovieDao
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class ListsViewModel(
    private val likedMovieDao: LikedMovieDao,
) : ViewModel() {

    private val _likedMovies = MutableLiveData<List<Movie>>()
    val likedMovies: LiveData<List<Movie>> get() = _likedMovies

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading



    fun loadLikedMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val likedMovieIds = likedMovieDao.getAll().map { it.id }
            val movies = mutableListOf<Movie>()
            for (id in likedMovieIds) {
                val movie = getMovieById(id)
                if (movie != null) {
                    movies.add(movie)
                }
            }
            _likedMovies.postValue(movies)
            _isLoading.value = false
        }
    }

    fun removeLikedMovie(movie: Movie) {
        viewModelScope.launch {
            likedMovieDao.delete(LikedMovie(id = movie.id))
            loadLikedMovies()
        }
    }



    init {
        loadLikedMovies()
    }


    private suspend fun getMovieById(id: Int): Movie? {
        val response = withContext(Dispatchers.IO) {
            val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
            val request = Request.Builder()
                .url("https://api.kinopoisk.dev/v1.4/movie/$id")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("X-API-KEY", "ER723RE-9CGMEMJ-PTGYK4B-1NG8B3N")
                .build()

            client.newCall(request).execute()
        }

        if (response.isSuccessful) {
            val body = response.body?.string()
            return Gson().fromJson(body, Movie::class.java)
        }
        return null
    }



}