package br.com.mfet.emovie.data.di

import br.com.mfet.emovie.data.api.retrofit.ApiClient
import br.com.mfet.emovie.presentation.repository.MovieRepository
import br.com.mfet.emovie.presentation.repository.MovieRepositoryImpl
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * This object organizes the data layer dependencies
 */
object DataModule {
    fun loadDataModule() {
        loadKoinModules(postsModule() + networkModule())
    }

    private fun postsModule(): Module {
        return module {
            single<MovieRepository> {
                MovieRepositoryImpl(
                    movieApi = get(),
                    refreshIntervalsMs = get()
                )
            }
        }
    }

    private fun networkModule(): Module {
        return module {
            single {
                get<ApiClient>().movieApi
            }
        }
    }
}