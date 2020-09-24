package com.lucasrodrigues.pokemonshowcase.dependencies

import com.lucasrodrigues.pokemonshowcase.BuildConfig
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import com.lucasrodrigues.pokemonshowcase.webservice.test.PokemonWebserviceTest
import org.koin.dsl.module

object WebserviceDependencies {
    val module = module(override = true) {
        if (BuildConfig.MOCK_DATA) {
            single<PokemonWebservice> { PokemonWebserviceTest() }
        } else {
            single<PokemonWebservice> { PokemonWebserviceTest() }
        }
    }
}