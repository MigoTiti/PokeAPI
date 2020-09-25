package com.lucasrodrigues.pokemonshowcase.extensions

import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Pokemon
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon

fun Pokemon.toDisplayPokemon(): DisplayPokemon {
    return DisplayPokemon(
        pokemonName = pokemonName,
        number = number,
        isFavorite = isFavorite
    )
}

fun DisplayPokemon.toPokemon(): Pokemon {
    return Pokemon(
        pokemonName = pokemonName,
        number = number
    )
}