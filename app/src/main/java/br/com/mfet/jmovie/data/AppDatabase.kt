package br.com.mfet.jmovie.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.mfet.jmovie.data.dao.MovieDao
import br.com.mfet.jmovie.data.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}