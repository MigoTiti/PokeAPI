package com.lucasrodrigues.pokemonshowcase.dependencies

import com.lucasrodrigues.pokemonshowcase.repository.PokemonRepository
import com.lucasrodrigues.pokemonshowcase.repository.RemoteKeysRepository
import org.koin.dsl.module

object RepositoryDependencies {
    val module = module(override = true) {
        single {
            PokemonRepository(
                pokemonDao = get(),
                pokemonWebservice = get(),
                abilityDao = get(),
                moveDao = get(),
                typeDao = get(),
                localDatabase = get(),
                pokemonAbilityDao = get(),
                pokemonMoveDao = get(),
                pokemonTypeDao = get()
            )
        }

        single {
            RemoteKeysRepository(
                remoteKeysDao = get(),
                database = get(),
                pokemonRepository = get()
            )
        }
    }
}