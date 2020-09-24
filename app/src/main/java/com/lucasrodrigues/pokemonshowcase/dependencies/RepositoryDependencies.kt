package com.lucasrodrigues.pokemonshowcase.dependencies

import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.pokemonshowcase.repository.PokemonRepository
import org.koin.dsl.module

@ExperimentalPagingApi
object RepositoryDependencies {
    val module = module(override = true) {
        single {
            PokemonRepository(
                pokemonDao = get(),
                pokemonWebservice = get()
            )
        }
    }
}