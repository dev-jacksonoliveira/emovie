package br.com.mfet.jmovie.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieInstance  {

    const val baseUrl = "https://api.themoviedb.org/3/movie/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiMovie = retrofit.create(MovieService::class.java)
}