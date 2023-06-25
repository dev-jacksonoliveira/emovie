package br.com.mfet.emovie.presentation.di

import br.com.mfet.emovie.presentation.MovieViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {
    fun loadViewModules() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule() : Module {
        return module {
            factory { MovieViewModel(movieRepository = get()) }
        }
    }
}
