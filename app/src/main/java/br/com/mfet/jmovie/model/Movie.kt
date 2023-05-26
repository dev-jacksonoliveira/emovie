package br.com.mfet.jmovie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
    val isFavorite: Boolean = false
)
