package br.com.mfet.jmovie.models

data class RecyclerList(val results: ArrayList<RecyclerData>)

data class RecyclerData(
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String
    )
