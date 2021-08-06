package br.com.mfet.jmovie.models

import androidx.room.ColumnInfo
import androidx.room.Entity

//@Entity(tableName = "favorites")
data class Movie(
    val id: Int,
//    @ColumnInfo(name = "title")
    val title: String,
//    @ColumnInfo(name = "title")
    val release_date: String,
//    @ColumnInfo(name = "title")
    val overview: String,
//    @ColumnInfo(name = "title")
    val poster_path: String
    )
