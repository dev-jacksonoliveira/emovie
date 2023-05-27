package br.com.mfet.jmovie.data.repository

import br.com.mfet.jmovie.data.PageList
import br.com.mfet.jmovie.data.model.Movie
import br.com.mfet.jmovie.data.api.ApiConst.API_KEY
import br.com.mfet.jmovie.data.api.ApiConst.API_KEY_QUERY
import br.com.mfet.jmovie.data.api.ApiConst.DEFAULT_LANGUAGE
import br.com.mfet.jmovie.data.api.ApiConst.LANGUAGE_QUERY
import br.com.mfet.jmovie.data.api.ApiConst.PAGE_QUERY
import br.com.mfet.jmovie.data.api.ApiConst.PATH_MOVIE_ID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    fun listPopular(
        @Query(API_KEY_QUERY) apiKey: String = API_KEY,
        @Query(LANGUAGE_QUERY) idiom: String = DEFAULT_LANGUAGE,
        @Query(PAGE_QUERY) page: Int = 1
    ): Call<PageList>

    @GET("upcoming")
    fun listUpcoming(
        @Query(API_KEY_QUERY) apiKey: String = API_KEY,
        @Query(LANGUAGE_QUERY) idiom: String = DEFAULT_LANGUAGE,
        @Query(PAGE_QUERY) page: Int = 1
    ): Call<PageList>

    @GET("{$PATH_MOVIE_ID}")
    fun getMovieById(
        @Path(PATH_MOVIE_ID) id: Int,
        @Query(API_KEY_QUERY) apiKey: String = API_KEY,
        @Query(LANGUAGE_QUERY) idiom: String = DEFAULT_LANGUAGE
    ): Call<Movie>

}

