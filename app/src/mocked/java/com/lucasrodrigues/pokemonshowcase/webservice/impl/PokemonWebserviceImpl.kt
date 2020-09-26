package com.lucasrodrigues.pokemonshowcase.webservice.impl

import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Ability
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Move
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Pokemon
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Type
import com.lucasrodrigues.pokemonshowcase.extensions.toDisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.*
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class PokemonWebserviceImpl : PokemonWebservice {

    private val abilitySize = 20
    private val typeSize = 10
    private val moveSize = 50

    private val pokemon = (1..893).map { index ->
        Pokemon(
            pokemonName = "Pokemon $index",
            number = index,
            sprites = listOf(
                PokemonSprite(
                    name = "front_default",
                    url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
                ),
                PokemonSprite(
                    name = "front_default",
                    url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
                ),
                PokemonSprite(
                    name = "front_default",
                    url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
                ),
                PokemonSprite(
                    name = "front_default",
                    url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
                )
            ),
            height = Random.nextInt(index).toDouble() / 10,
            weight = Random.nextInt(index).toDouble() / 10,
            baseStats = listOf(
                PokemonStat("hp", Random.nextInt(100)),
                PokemonStat("attack", Random.nextInt(100)),
                PokemonStat("defense", Random.nextInt(100)),
                PokemonStat("special-attack", Random.nextInt(100)),
                PokemonStat("special-defense", Random.nextInt(100)),
                PokemonStat("speed", Random.nextInt(100)),
            )
        )
    }

    private val abilities = (0..abilitySize).map { index ->
        Ability(
            abilityId = index,
            name = "Ability $index"
        )
    }

    private val types = (0..typeSize).map { index ->
        Type(
            typeId = index,
            name = "Type $index"
        )
    }

    private val moves = (0..moveSize).map { index ->
        Move(
            moveId = index,
            name = "Move $index"
        )
    }

    private val pokemonDetailed = pokemon.map {
        PokemonDetailed(
            pokemon = it,
            abilities = abilities.take(
                Random.nextInt(abilitySize)
            ),
            types = types.take(
                Random.nextInt(typeSize)
            ),
            moves = moves.take(
                Random.nextInt(moveSize)
            ),
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

        val result = pokemonDetailed.subList(absoluteOffset, generationUpperBound).take(pageSize)

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
                it.pokemon.toDisplayPokemon()
            }.toList(),
        )
    }

    override suspend fun searchPokemon(name: String): PokemonDetailed {
        delay(500L)

        val success = true

        if (!success)
            throw Exception("Exception 1")

        return pokemonDetailed.singleOrNull {
            it.pokemon.pokemonName == name
        } ?: throw Exception("Pokemon não encontrado")
    }
}