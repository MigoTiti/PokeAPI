package com.lucasrodrigues.pokemonshowcase

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.pokemonshowcase.dependencies.DataAccessDependencies
import com.lucasrodrigues.pokemonshowcase.dependencies.RepositoryDependencies
import com.lucasrodrigues.pokemonshowcase.dependencies.WebserviceDependencies
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalPagingApi
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
                )
            )
        }
    }
}
