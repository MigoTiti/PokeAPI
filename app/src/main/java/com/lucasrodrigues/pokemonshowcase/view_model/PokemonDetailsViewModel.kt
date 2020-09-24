package com.lucasrodrigues.pokemonshowcase.view_model

import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.pokemonshowcase.framework.AlertService
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService
import com.lucasrodrigues.pokemonshowcase.model.LoadingState
import com.lucasrodrigues.pokemonshowcase.repository.PokemonRepository

@ExperimentalPagingApi
class PokemonDetailsViewModel(
    navigationService: NavigationService,
    alertService: AlertService,
    private val pokemonRepository: PokemonRepository,
    private val pokemonName: String
) : BaseViewModel(navigationService, alertService) {

    val pokemonDetails = pokemonRepository.listenToPokemon(pokemonName)

    val fetchingPokemonState = MutableLiveData<LoadingState>(LoadingState.Idle)

    fun fetchPokemon() {
        request(
            loadingLiveData = fetchingPokemonState,
            showErrorAlert = false,
        ) {
            pokemonRepository.fetchPokemon(pokemonName)
        }
    }
}