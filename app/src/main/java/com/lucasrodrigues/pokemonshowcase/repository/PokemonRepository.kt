package com.lucasrodrigues.pokemonshowcase.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lucasrodrigues.pokemonshowcase.data_access.local.dao.PokemonDao
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.Pokemon
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.flow.Flow

class PokemonRepository(
    private val pokemonDao: PokemonDao,
    private val pokemonWebservice: PokemonWebservice
) {
    fun listenToPokemon(name: String): LiveData<Pokemon> {
        return pokemonDao.selectPokemonByIdLiveData(name)
    }

    fun listenToFavoritePokemonList(): LiveData<List<DisplayPokemon>> {
        return pokemonDao.getAllFavoritePokemon()
    }

    fun allPokemonPagedList(): Flow<PagingData<DisplayPokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20)
        ) {
            pokemonDao.selectAllDisplayPokemonPagingSource()
        }.flow
    }

    suspend fun fetchPokemon(name: String) {
        val hasDetailedPokemon = pokemonDao.hasDetailedPokemon(name)

        if (!hasDetailedPokemon) {
            pokemonDao.insertPokemonPreservingFavoriteFlag(
                pokemonWebservice.searchPokemon(name)
            )
        }
    }

    suspend fun toggleFavoritePokemon(pokemon: Pokemon) {
        pokemonDao.toggleFavoriteFlag(pokemon)
    }

    suspend fun toggleFavoritePokemon(pokemon: DisplayPokemon) {
        toggleFavoritePokemon(pokemon.name)
    }

    suspend fun toggleFavoritePokemon(name: String) {
        pokemonDao.toggleFavoriteFlag(name)
    }
}