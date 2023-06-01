package br.com.mfet.emovie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    @SerializedName("release_date") val releaseDate: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    val isFavorite: Boolean = false
)
