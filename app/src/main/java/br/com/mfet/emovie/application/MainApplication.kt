package br.com.mfet.emovie.application

import android.app.Application
import br.com.mfet.emovie.data.di.DataModule
import br.com.mfet.emovie.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            PresentationModule.loadViewModules()
            DataModule.loadDataModule()
        }
    }
}