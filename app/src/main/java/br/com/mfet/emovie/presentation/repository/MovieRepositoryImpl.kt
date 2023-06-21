package br.com.mfet.emovie.presentation.repository

import br.com.mfet.emovie.data.model.Movie
import br.com.mfet.emovie.data.repository.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val refreshIntervalsMs: Long = VALUE_INTERVAL_MS
) : MovieRepository {

    override fun getListPopularMovies(): Flow<List<Movie>> {
        return flow {
            while (true) {
                val popularMoviesList = movieApi.fetchPopularMoviesList()
                emit(popularMoviesList)
                kotlinx.coroutines.delay(refreshIntervalsMs)
            }
        }
    }

    companion object {
        private const val VALUE_INTERVAL_MS = 5000L
    }

}