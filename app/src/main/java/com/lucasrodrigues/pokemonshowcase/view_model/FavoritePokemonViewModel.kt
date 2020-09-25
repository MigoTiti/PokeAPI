package com.lucasrodrigues.pokemonshowcase.view_model

import com.lucasrodrigues.pokemonshowcase.framework.AlertService
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.repository.PokemonRepository

class FavoritePokemonViewModel constructor(
    navigationService: NavigationService,
    alertService: AlertService,
    private val pokemonRepository: PokemonRepository
) : BaseViewModel(navigationService, alertService) {

    val favoritePokemon = pokemonRepository.listenToFavoritePokemonList()

    fun toggleFavorite(pokemon: DisplayPokemon) {
        request {
            pokemonRepository.toggleFavoritePokemon(pokemon)
        }
    }
}