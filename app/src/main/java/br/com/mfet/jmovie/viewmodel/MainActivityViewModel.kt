package br.com.mfet.jmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mfet.jmovie.models.RecyclerList
import br.com.mfet.jmovie.network.RetroInstance
import br.com.mfet.jmovie.network.RetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
    lateinit var recyclerListLiveData : MutableLiveData<RecyclerList>

    companion object {
        const val API_KEY = "ad802fbc8a9b272ff3f9e783088f346e"
    }

    init {
        recyclerListLiveData = MutableLiveData()
    }

    fun getRecyclerListObserver(): MutableLiveData<RecyclerList> {
        return recyclerListLiveData
    }


    fun makeApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
            val response = retroInstance.getDataFromApi(API_KEY)
            val responseLatest = retroInstance.getLatestFromApi(API_KEY)

            recyclerListLiveData.postValue(response)
            recyclerListLiveData.postValue(responseLatest)
        }
    }
}