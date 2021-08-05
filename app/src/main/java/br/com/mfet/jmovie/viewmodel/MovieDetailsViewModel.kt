package br.com.mfet.jmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mfet.jmovie.models.Movie
import br.com.mfet.jmovie.repository.MovieInstance
import br.com.mfet.jmovie.repository.MovieService
import br.com.mfet.jmovie.viewmodel.MainActivityViewModel.Companion.MOVIE_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {
    var movieLiveData : MutableLiveData<Movie>
    init {
        movieLiveData = MutableLiveData()
    }

    fun addObserver(): MutableLiveData<Movie> {
        return movieLiveData
    }

    fun getApiMovieCall() {
        viewModelScope.launch(Dispatchers.IO) {
            val movieInstance = MovieInstance.getMovieInstance().create(MovieService::class.java)
            val response = movieInstance.getMovieById(631843)

            movieLiveData.postValue(response)
        }

    }
}