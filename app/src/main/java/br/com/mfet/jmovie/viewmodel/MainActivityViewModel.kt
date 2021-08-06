package br.com.mfet.jmovie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mfet.jmovie.models.Movie
import br.com.mfet.jmovie.repository.MovieInstance
import br.com.mfet.jmovie.repository.PageList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {
    private val movieLiveDataPopular : MutableLiveData<List<Movie>?> = MutableLiveData()
    private val movieLiveDataUpComing : MutableLiveData<List<Movie>?> = MutableLiveData()


    fun getPopularObserver(): MutableLiveData<List<Movie>?> {
        return movieLiveDataPopular
    }

    fun getUpComingObserver(): MutableLiveData<List<Movie>?> {
        return movieLiveDataUpComing
    }

    fun movieListApiCall() {
        viewModelScope.launch(Dispatchers.IO) {

            val callPopular = MovieInstance.apiMovie.listPopular()
            callPopular.enqueue(object : Callback<PageList> {
                override fun onResponse(call: Call<PageList>, response: Response<PageList>) {

                    movieLiveDataPopular.postValue(response.body()?.results)
                }

                override fun onFailure(call: Call<PageList>, t: Throwable) {
                    Log.d("TAG", "Erro")
                }
            })
            val callUpcoming = MovieInstance.apiMovie.listUpcoming()

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