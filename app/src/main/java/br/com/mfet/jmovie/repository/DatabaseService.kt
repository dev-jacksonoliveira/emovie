package br.com.mfet.jmovie.repository

import android.content.Context
import androidx.room.Room
import br.com.mfet.jmovie.data.db.AppDatabase
import br.com.mfet.jmovie.model.Movie
import kotlinx.coroutines.*

object DatabaseService {
    var database : AppDatabase? = null

    fun initDatabase(context: Context) {
        if(database == null) {
            database = Room.databaseBuilder(context, AppDatabase::class.java, "movie_app")
                .build()
        }
    }

    fun setMovieFavorite(context: Context, movie: Movie) {
        initDatabase(context)
        CoroutineScope(GlobalScope.coroutineContext).launch {
            withContext(Dispatchers.IO) {
                database?.movieDao()?.insert(movie)
            }
        }
    }
    fun deleteMovieFavorite(context: Context, movie: Movie) {
        initDatabase(context)
        CoroutineScope(GlobalScope.coroutineContext).launch {
            withContext(Dispatchers.IO) {
                database?.movieDao()?.deleteAll(movie)
            }
        }
    }
    fun getMovieFavorite(context: Context, callback: (List<Movie>) -> Unit) {
        initDatabase(context)
        CoroutineScope(GlobalScope.coroutineContext).launch {
            withContext(Dispatchers.IO) {
                val listFavorites = database?.movieDao()?.getMovieFavorites()
                withContext(Dispatchers.Main) {

                    callback(listFavorites?: listOf())
                }
            }
        }
    }
}