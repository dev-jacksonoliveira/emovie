package br.com.mfet.emovie.di

import br.com.mfet.emovie.data.api.retrofit.ApiClient
import br.com.mfet.emovie.presentation.MovieViewModel
import br.com.mfet.emovie.presentation.repository.MovieRepository
import br.com.mfet.emovie.presentation.repository.MovieRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { ApiClient.movieApi }
    factory<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    viewModel { MovieViewModel(get()) }
}