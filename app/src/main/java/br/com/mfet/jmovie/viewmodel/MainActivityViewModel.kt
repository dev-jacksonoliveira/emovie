package br.com.mfet.jmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mfet.jmovie.models.Movie
import br.com.mfet.jmovie.repository.ApiConst.API_KEY
import br.com.mfet.jmovie.repository.MovieInstance
import br.com.mfet.jmovie.repository.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
    lateinit var movieLiveData : MutableLiveData<Movie>

//    companion object {
//        const val API_KEY = "ad802fbc8a9b272ff3f9e783088f346e"
//    }

    companion object {
        const val MOVIE_ID = "br.com.mfet.jmovie.view.activity.MainActivityViewModel.MOVIE_ID"
    }

    init {
        movieLiveData = MutableLiveData()
    }

    fun getRecyclerListObserver(): MutableLiveData<Movie> {
        return movieLiveData
    }


    fun makeApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = MovieInstance.getMovieInstance().create(MovieService::class.java)
            val response = retroInstance.getPopularService(API_KEY)
            val responseLatest = retroInstance.getUpcomingService(API_KEY)

            movieLiveData.postValue(response)
            movieLiveData.postValue(responseLatest)
        }
    }
}