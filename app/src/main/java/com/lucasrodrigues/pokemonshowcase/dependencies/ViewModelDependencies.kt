package com.lucasrodrigues.pokemonshowcase.dependencies

import android.app.Activity
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.pokemonshowcase.view_model.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

@ExperimentalPagingApi
object ViewModelDependencies {
    val module = module(override = true) {
        viewModel { (activity: Activity) ->
            MainViewModel(
                navigationService = get { parametersOf(activity) },
                alertService = get { parametersOf(activity) },
                pokemonRepository = get(),
                remoteKeysRepository = get()
            )
        }
    }
}