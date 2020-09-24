package com.lucasrodrigues.pokemonshowcase.webservice.test

import com.lucasrodrigues.pokemonshowcase.extensions.toDisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.Pokemon
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class PokemonWebserviceTest : PokemonWebservice {

    val pokemon = (1..151).map { index ->
        Pokemon(
            name = "pokemon$index",
            number = index,
            abilities = (0..Random.Default.nextInt(5)).map {
                "Habilidade $it"
            },
            sprites = listOf(),
            height = index,
            weight = index,
            baseExperience = index,
        )
    }

    override suspend fun fetchAllPokemon(offset: Int, pageSize: Int): PagedPokemonList {
        delay(if (offset == 0) 2000L else 500L)

        val success = true

        if (!success)
            throw Exception("Exception 1")

        val result = pokemon.subList(offset, pokemon.size).take(pageSize)

        return PagedPokemonList(
            previousOffset = if (offset == 0)
                null
            else
                max(0, offset - pageSize),
            nextOffset = if (offset + result.size == pokemon.size)
                null
            else
                min(pokemon.size - 1, offset + pageSize),
            pokemon = result.map {
                it.toDisplayPokemon()
            }.toList(),
        )
    }

    override suspend fun searchPokemon(name: String): Pokemon {
        delay(500L)

        val success = true

        if (!success)
            throw Exception("Exception 1")

        return pokemon.singleOrNull {
            it.name == name
        } ?: throw Exception("Pokemon não encontrado")
    }
}