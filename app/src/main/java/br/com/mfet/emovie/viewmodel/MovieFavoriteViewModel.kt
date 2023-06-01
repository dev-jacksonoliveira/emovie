package br.com.mfet.emovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.mfet.emovie.data.model.Movie

class MovieFavoriteViewModel : ViewModel() {
    private val movieLiveDataFavorite: MutableLiveData<List<Movie>?> = MutableLiveData()
}