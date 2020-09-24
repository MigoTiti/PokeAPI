package com.lucasrodrigues.pokemonshowcase.framework

interface NavigationService {
    fun navigateToPokemonDetails(pokemonName: String)
    fun navigateToFavoritePokemon()
    fun goBack()
}