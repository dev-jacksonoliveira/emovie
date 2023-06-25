package br.com.mfet.emovie.data.repository

import br.com.mfet.emovie.data.PageList
import br.com.mfet.emovie.data.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("popular")
    suspend fun fetchPopularMoviesList(
        @Query(API_KEY_QUERY) apiKey: String = API_KEY,
        @Query(LANGUAGE_QUERY) idiom: String = DEFAULT_LANGUAGE,
        @Query(PAGE_QUERY) page: Int = 1
    ): List<Movie>

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

    companion object {
        private const val API_KEY = "a3ab39cfa47a650d8da5aeacb8bd67b8"
        private const val PATH_MOVIE_ID = "id"
        private const val API_KEY_QUERY = "api_key"
        private const val LANGUAGE_QUERY = "language"
        private const val DEFAULT_LANGUAGE = "pt-BR"
        private const val PAGE_QUERY = "page"
    }
}

