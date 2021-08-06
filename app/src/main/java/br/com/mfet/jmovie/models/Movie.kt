package br.com.mfet.jmovie.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Movie(
    @PrimaryKey
     val id: Int,

    val title: String,

    val release_date: String,

    val overview: String,

    val poster_path: String,

    val isFavorite: Boolean = false
    )
