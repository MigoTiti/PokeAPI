package com.lucasrodrigues.pokemonshowcase.dependencies

import android.app.Activity
import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.view_model.FavoritePokemonViewModel
import com.lucasrodrigues.pokemonshowcase.view_model.GenerationPokemonViewModel
import com.lucasrodrigues.pokemonshowcase.view_model.MainViewModel
import com.lucasrodrigues.pokemonshowcase.view_model.PokemonDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

object ViewModelDependencies {
    val module = module(override = true) {
        viewModel { (activity: Activity) ->
            MainViewModel(
                navigationService = get { parametersOf(activity) },
                alertService = get { parametersOf(activity) }
            )
        }

        viewModel { (activity: Activity, generation: Generation) ->
            GenerationPokemonViewModel(
                navigationService = get { parametersOf(activity) },
                alertService = get { parametersOf(activity) },
                pokemonRepository = get(),
                remoteKeysRepository = get(),
                generation = generation
            )
        }

        viewModel { (activity: Activity, pokemonName: String) ->
            PokemonDetailsViewModel(
                navigationService = get { parametersOf(activity) },
                alertService = get { parametersOf(activity) },
                pokemonRepository = get(),
                pokemonName = pokemonName
            )
        }

        viewModel { (activity: Activity) ->
            FavoritePokemonViewModel(
                navigationService = get { parametersOf(activity) },
                alertService = get { parametersOf(activity) },
                pokemonRepository = get()
            )
        }
    }
}