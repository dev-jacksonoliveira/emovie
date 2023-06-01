package br.com.mfet.emovie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mfet.emovie.data.model.Movie
import br.com.mfet.emovie.data.api.retrofit.ApiClient
import br.com.mfet.emovie.data.PageList
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    private val movieLiveDataPopular: MutableLiveData<List<Movie>?> = MutableLiveData()
    private val movieLiveDataUpComing: MutableLiveData<List<Movie>?> = MutableLiveData()
    private val movieLiveDataFavorite: MutableLiveData<List<Movie>?> = MutableLiveData()

    fun getPopularObserver(): MutableLiveData<List<Movie>?> {
        return movieLiveDataPopular
    }

    fun getUpComingObserver(): MutableLiveData<List<Movie>?> {
        return movieLiveDataUpComing
    }

    fun getFavoriteObserver(): MutableLiveData<List<Movie>?> {
        return movieLiveDataFavorite
    }

    fun movieListApiCall() {
        viewModelScope.launch(Dispatchers.IO) {

            val callPopular = ApiClient.apiService.listPopular()
            callPopular.enqueue(object : Callback<PageList> {
                override fun onResponse(call: Call<PageList>, response: Response<PageList>) {

                    movieLiveDataPopular.postValue(response.body()?.results)
                }

                override fun onFailure(call: Call<PageList>, t: Throwable) {
                    Log.d("TAG", "Erro")
                }
            })
            val callUpcoming = ApiClient.apiService.listUpcoming()

            callUpcoming.enqueue(object : Callback<PageList> {
                override fun onResponse(call: Call<PageList>, response: Response<PageList>) {

                    movieLiveDataUpComing.postValue(response.body()?.results)
                }

                override fun onFailure(call: Call<PageList>, t: Throwable) {
                    Log.d("TAG", "Erro")
                }
            })
        }
    }




}