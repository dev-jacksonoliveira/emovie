package br.com.mfet.jmovie.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetroInstance {

    companion object {
        val baseUrl = "https://api.themoviedb.org/3/movie/"

        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}