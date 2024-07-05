package com.example.animetinder.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animetinder.data.Movie
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class MovieDetailsViewModel : ViewModel() {
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading



    fun getMovieById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
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
                val movie = Gson().fromJson(body, Movie::class.java)
                _movie.postValue(movie)
            }
            _isLoading.value = false
        }
    }
}