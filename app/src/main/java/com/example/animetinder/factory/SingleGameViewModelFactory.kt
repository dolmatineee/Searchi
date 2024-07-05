package com.example.animetinder.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animetinder.viewmodels.SingleGameViewModel
import com.example.animetinder.data.LikedMovieDao

class SingleGameViewModelFactory(
    private val likedMovieDao: LikedMovieDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SingleGameViewModel(likedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}