package br.com.mfet.emovie.presentation.repository

import br.com.mfet.emovie.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getListPopularMovies(): Flow<List<Movie>>

}