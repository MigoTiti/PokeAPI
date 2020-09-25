package com.lucasrodrigues.pokemonshowcase

import android.app.Application
import com.lucasrodrigues.pokemonshowcase.dependencies.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    DataAccessDependencies.module,
                    WebserviceDependencies.module,
                    RepositoryDependencies.module,
                    FrameworkDependencies.module,
                    ViewModelDependencies.module,
                )
            )
        }
    }
}
