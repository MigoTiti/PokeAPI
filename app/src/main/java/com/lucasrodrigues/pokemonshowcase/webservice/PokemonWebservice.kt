package com.lucasrodrigues.pokemonshowcase.webservice

import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.Pokemon

interface PokemonWebservice {
    suspend fun fetchAllPokemon(offset: Int, pageSize: Int): PagedPokemonList

    suspend fun searchPokemon(name: String): Pokemon
}