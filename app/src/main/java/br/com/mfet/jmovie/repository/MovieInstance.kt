package br.com.mfet.jmovie.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MovieInstance {

    companion object {
        val baseUrl = "https://api.themoviedb.org/3/movie/"

        fun getMovieInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}