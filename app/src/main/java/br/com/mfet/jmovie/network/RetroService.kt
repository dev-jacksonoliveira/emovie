package br.com.mfet.jmovie.network

import br.com.mfet.jmovie.models.RecyclerList
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("popular")
    suspend fun getDataFromApi(@Query("api_key") query: String) : RecyclerList

    @GET("upcoming")
    suspend fun getLatestFromApi(@Query("api_key")query: String) : RecyclerList
}