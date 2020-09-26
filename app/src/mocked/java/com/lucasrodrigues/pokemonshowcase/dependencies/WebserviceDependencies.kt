package com.lucasrodrigues.pokemonshowcase.dependencies

import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import com.lucasrodrigues.pokemonshowcase.webservice.impl.PokemonWebserviceImpl
import org.koin.dsl.module

object WebserviceDependencies {
    val module = module(override = true) {
        single<PokemonWebservice> { PokemonWebserviceImpl() }
    }
}