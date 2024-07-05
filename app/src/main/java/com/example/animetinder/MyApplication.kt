package com.example.animetinder

import android.app.Application
import androidx.room.Room
import com.example.animetinder.data.AppDatabase

class MyApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my-database"
        ).build()
    }
}