package com.lucasrodrigues.pokemonshowcase.data_access.network.data

data class PokemonData(
    val id: Int,
    val order: Int,
    val base_experience: Int,
    val height: Int,
    val weight: Int,
    val is_default: Boolean,
    val name: String,
    val moves: List<MoveInsidePokemonData>,
    val abilities: List<AbilityInsidePokemonData>,
    val types: List<TypeInsidePokemonData>,
    val stats: List<StatInsidePokemonData>,
    val sprites: Map<String, Any?>
)