package com.example.animetinder.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animetinder.viewmodels.ListsViewModel
import com.example.animetinder.data.LikedMovieDao

class ListsViewModelFactory(
    private val likedMovieDao: LikedMovieDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListsViewModel(likedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}