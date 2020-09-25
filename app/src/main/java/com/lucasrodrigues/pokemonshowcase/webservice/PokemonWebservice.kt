package com.lucasrodrigues.pokemonshowcase.webservice

import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Ability
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Move
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Type
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.PokemonWithIds

interface PokemonWebservice {
    suspend fun fetchAllPokemon(
        generation: Generation,
        generationRelativeOffset: Int,
        pageSize: Int
    ): PagedPokemonList

    suspend fun searchPokemon(name: String): PokemonWithIds

    suspend fun fetchAbility(id: Int): Ability

    suspend fun fetchMove(id: Int): Move

    suspend fun fetchType(id: Int): Type
}