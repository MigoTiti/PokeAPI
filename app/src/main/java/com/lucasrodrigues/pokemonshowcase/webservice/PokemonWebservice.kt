package com.lucasrodrigues.pokemonshowcase.webservice

import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.Pokemon

interface PokemonWebservice {
    suspend fun fetchAllPokemon(
        generation: Generation,
        generationRelativeOffset: Int,
        pageSize: Int
    ): PagedPokemonList

    suspend fun searchPokemon(name: String): Pokemon
}