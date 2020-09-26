package com.lucasrodrigues.pokemonshowcase.view_model

import androidx.lifecycle.MutableLiveData
import com.lucasrodrigues.pokemonshowcase.framework.AlertService
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService
import com.lucasrodrigues.pokemonshowcase.model.LoadingState
import com.lucasrodrigues.pokemonshowcase.repository.PokemonRepository

class PokemonDetailsViewModel(
    navigationService: NavigationService,
    alertService: AlertService,
    private val pokemonRepository: PokemonRepository,
    val pokemonName: String
) : BaseViewModel(navigationService, alertService) {

    val pokemonDetails = pokemonRepository.listenToPokemon(pokemonName)

    val fetchingPokemonState = MutableLiveData<LoadingState>()

    fun fetchPokemon() {
        request(
            loadingLiveData = fetchingPokemonState,
            showErrorAlert = false,
        ) {
            pokemonRepository.updatePokemon(pokemonName)
        }
    }

    fun toggleFavorite() {
        val pokemon = pokemonDetails.value

        if (pokemon != null)
            request {
                pokemonRepository.toggleFavoritePokemon(pokemon.pokemon)
            }
    }
}