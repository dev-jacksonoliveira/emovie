package br.com.mfet.emovie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.mfet.emovie.data.dao.MovieDao
import br.com.mfet.emovie.data.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}