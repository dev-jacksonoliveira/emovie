package br.com.mfet.jmovie.data.dao

import androidx.room.*
import br.com.mfet.jmovie.data.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorites")
    fun getMovieFavorites(): List<Movie>

    @Insert
    fun insert(movie: Movie)

    @Delete
    fun deleteAll(movie: Movie)
}