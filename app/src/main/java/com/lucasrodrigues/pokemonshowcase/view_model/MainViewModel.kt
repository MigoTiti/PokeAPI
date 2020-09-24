package com.lucasrodrigues.pokemonshowcase.view_model

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lucasrodrigues.pokemonshowcase.framework.AlertService
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.repository.PokemonRepository
import com.lucasrodrigues.pokemonshowcase.repository.RemoteKeysRepository
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class MainViewModel(
    navigationService: NavigationService,
    alertService: AlertService,
    private val pokemonRepository: PokemonRepository,
    private val remoteKeysRepository: RemoteKeysRepository
) : BaseViewModel(navigationService, alertService) {

    fun getAllPokemon(): Flow<PagingData<DisplayPokemon>> {
        return pokemonRepository
            .allPokemonPagedList(remoteKeysRepository)
            .cachedIn(viewModelScope)
    }
}