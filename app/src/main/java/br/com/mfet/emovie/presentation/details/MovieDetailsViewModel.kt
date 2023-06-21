package br.com.mfet.emovie.presentation.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mfet.emovie.data.model.Movie
import br.com.mfet.emovie.data.api.retrofit.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsViewModel : ViewModel() {
    private val movieLiveData: MutableLiveData<Movie?> = MutableLiveData()

    fun getMovie(): MutableLiveData<Movie?> {
        return movieLiveData
    }

    fun getApiMovieCall(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val call = ApiClient.movieApi.getMovieById(id)
            call.enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {

                    movieLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("TAG", "Erro")
                }
            })
        }
    }
}