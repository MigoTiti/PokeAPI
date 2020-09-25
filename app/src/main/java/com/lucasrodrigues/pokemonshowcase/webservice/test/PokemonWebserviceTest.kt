package com.lucasrodrigues.pokemonshowcase.webservice.test

import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.extensions.toDisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.Pokemon
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class PokemonWebserviceTest : PokemonWebservice {

    val pokemon = (1..893).map { index ->
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

    override suspend fun fetchAllPokemon(
        generation: Generation,
        generationRelativeOffset: Int,
        pageSize: Int
    ): PagedPokemonList {
        delay(if (generationRelativeOffset == 0) 2000L else 500L)

        val success = true

        if (!success)
            throw Exception("Exception 1")

        val generationLowerBound = generation.lowerBound() - 1
        val generationUpperBound = generation.upperBound()

        val absoluteOffset = generationRelativeOffset + generationLowerBound

        val result = pokemon.subList(absoluteOffset, generationUpperBound).take(pageSize)

        return PagedPokemonList(
            previousOffset = if (absoluteOffset == generationLowerBound)
                null
            else
                max(0, generationRelativeOffset - pageSize),
            nextOffset = if (absoluteOffset + result.size >= generationUpperBound - 1)
                null
            else
                min(generationUpperBound - 1, generationRelativeOffset + pageSize),
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