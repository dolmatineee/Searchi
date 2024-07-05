package com.example.animetinder.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class LikedMovie(
    @PrimaryKey val id: Int
)

@Dao
interface LikedMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: LikedMovie)

    @Query("SELECT * FROM likedmovie")
    suspend fun getAll(): List<LikedMovie>

    @Delete
    suspend fun delete(movie: LikedMovie)
}

@Database(entities = [LikedMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likedMovieDao(): LikedMovieDao
}