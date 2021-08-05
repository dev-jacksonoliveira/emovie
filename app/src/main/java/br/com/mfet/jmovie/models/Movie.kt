package br.com.mfet.jmovie.models

data class Movie(val results: ArrayList<MovieData>)

data class MovieData(
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String
    )
