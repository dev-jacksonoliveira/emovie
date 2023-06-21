package br.com.mfet.emovie.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mfet.emovie.data.model.Movie
import br.com.mfet.emovie.presentation.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val movieLiveDataPopular: MutableLiveData<List<Movie>?> = MutableLiveData()
    private val movieLiveDataUpComing: MutableLiveData<List<Movie>?> = MutableLiveData()
    private val movieLiveDataFavorite: MutableLiveData<List<Movie>?> = MutableLiveData()

    private val _uiState = MutableStateFlow(MovieUiState.Success(emptyList()))
    val uiState: StateFlow<MovieUiState> = _uiState

    init {
        viewModelScope.launch {
            movieRepository.getListPopularMovies().collect { popularMovies ->
                _uiState.value = MovieUiState.Success(popularMovies)
            }
        }
    }

//        fun getPopularObserver(): MutableLiveData<List<Movie>?> {
//            return movieLiveDataPopular
//        }
//
//        fun getUpComingObserver(): MutableLiveData<List<Movie>?> {
//            return movieLiveDataUpComing
//        }
//
//        fun getFavoriteObserver(): MutableLiveData<List<Movie>?> {
//            return movieLiveDataFavorite
//        }
//
//        fun movieListApiCall() {
//            viewModelScope.launch(Dispatchers.IO) {
//
//
////            val callPopular = ApiClient.movieApi.fetchPopularMoviesList()
////            callPopular.enqueue(object : Callback<PageList> {
////                override fun onResponse(call: Call<PageList>, response: Response<PageList>) {
////
////                    movieLiveDataPopular.postValue(response.body()?.results)
////                }
////
////                override fun onFailure(call: Call<PageList>, t: Throwable) {
////                    Log.d("TAG", "Erro")
////                }
////            })
//                val callUpcoming = ApiClient.movieApi.listUpcoming()
//
//                callUpcoming.enqueue(object : Callback<PageList> {
//                    override fun onResponse(call: Call<PageList>, response: Response<PageList>) {
//
//                        movieLiveDataUpComing.postValue(response.body()?.results)
//                    }
//
//                    override fun onFailure(call: Call<PageList>, t: Throwable) {
//                        Log.d("TAG", "Erro")
//                    }
//                })
//            }
//        }
//
//    }
}