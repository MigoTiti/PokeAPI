package com.lucasrodrigues.pokemonshowcase.webservice.impl

import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.data_access.network.PokemonAPI
import com.lucasrodrigues.pokemonshowcase.extensions.toPagedPokemonList
import com.lucasrodrigues.pokemonshowcase.extensions.toPokemonDetailed
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.PokemonDetailed
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlin.math.min

class PokemonWebserviceImpl(
    private val pokemonAPI: PokemonAPI
) : PokemonWebservice {
    override suspend fun fetchAllPokemon(
        generation: Generation,
        generationRelativeOffset: Int,
        pageSize: Int
    ): PagedPokemonList {
        return pokemonAPI.fetchPokemonList(
            offset = generation.lowerBound() - 1 + generationRelativeOffset,
            pageSize = min(pageSize, generation.size() - generationRelativeOffset)
        ).toPagedPokemonList(generation)
    }

    override suspend fun searchPokemon(name: String): PokemonDetailed {
        return pokemonAPI.fetchPokemonDetails(name).toPokemonDetailed()
    }
}