package com.lucasrodrigues.pokemonshowcase.extensions

import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.Pokemon

fun Pokemon.toDisplayPokemon(): DisplayPokemon {
    return DisplayPokemon(
        name = name,
        number = number
    )
}