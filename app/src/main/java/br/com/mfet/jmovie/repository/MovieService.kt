package br.com.mfet.jmovie.repository

import br.com.mfet.jmovie.models.Movie
import br.com.mfet.jmovie.repository.ApiConst.DEFAULT_QUERY
import br.com.mfet.jmovie.repository.ApiConst.PATH_MOVIE_ID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("popular")
    suspend fun getPopularService(@Query(DEFAULT_QUERY) query: String) : Movie

    @GET("upcoming")
    suspend fun getUpcomingService(@Query(DEFAULT_QUERY) query: String) : Movie

    @GET("{$PATH_MOVIE_ID}")
//    suspend fun getMovieById(@Query(DEFAULT_QUERY) query: String) : Movie
    suspend fun getMovieById(@Path(PATH_MOVIE_ID) id: Int): Movie
}