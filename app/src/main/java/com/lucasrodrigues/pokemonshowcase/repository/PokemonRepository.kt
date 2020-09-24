package com.lucasrodrigues.pokemonshowcase.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lucasrodrigues.pokemonshowcase.components.PokemonRemoteMediator
import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.data_access.local.dao.PokemonDao
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.Pokemon
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
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

    fun allPokemonPagedList(
        remoteKeysRepository: RemoteKeysRepository,
        generation: Generation
    ): Flow<PagingData<DisplayPokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PokemonRemoteMediator(
                pokemonRepository = this,
                remoteKeysRepository = remoteKeysRepository,
                generation = generation
            )
        ) {
            pokemonDao.selectAllDisplayPokemonPagingSource(
                begin = generation.lowerBound(),
                end = generation.upperBound()
            )
        }.flow
    }

    suspend fun fetchPokemonPagedList(
        generation: Generation,
        offset: Int = 0,
        pageSize: Int
    ): PagedPokemonList {
        return pokemonWebservice.fetchAllPokemon(generation, offset, pageSize)
    }

    suspend fun fetchPokemon(name: String) {
        val hasDetailedPokemon = pokemonDao.hasDetailedPokemon(name)

        if (!hasDetailedPokemon) {
            insertPokemon(pokemonWebservice.searchPokemon(name))
        }
    }

    suspend fun insertPokemon(vararg pokemon: Pokemon) {
        pokemonDao.insertOrUpdatePokemonPreservingFavoriteFlag(*pokemon)
    }

    suspend fun insertPokemon(vararg pokemon: DisplayPokemon) {
        pokemonDao.insertIfNotPresent(*pokemon)
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