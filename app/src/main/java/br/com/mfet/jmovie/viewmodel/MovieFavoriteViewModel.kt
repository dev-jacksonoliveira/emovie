package br.com.mfet.jmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.mfet.jmovie.data.model.Movie

class MovieFavoriteViewModel : ViewModel() {
    private val movieLiveDataFavorite: MutableLiveData<List<Movie>?> = MutableLiveData()
}