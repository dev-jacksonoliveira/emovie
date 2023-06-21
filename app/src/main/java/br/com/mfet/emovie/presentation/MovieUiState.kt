package br.com.mfet.emovie.presentation

import br.com.mfet.emovie.data.model.Movie

sealed class MovieUiState {
    data class Success(val popularMovies: List<Movie>) : MovieUiState()
    data class Error(val exception: Throwable): MovieUiState()
}
