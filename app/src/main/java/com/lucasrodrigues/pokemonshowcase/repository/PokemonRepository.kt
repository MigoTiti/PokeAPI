package com.lucasrodrigues.pokemonshowcase.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.lucasrodrigues.pokemonshowcase.components.PokemonRemoteMediator
import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.data_access.local.LocalDatabase
import com.lucasrodrigues.pokemonshowcase.data_access.local.dao.*
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Pokemon
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonAbilityCrossRef
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonMoveCrossRef
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonTypeCrossRef
import com.lucasrodrigues.pokemonshowcase.extensions.normalizeToQuery
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.PokemonDetailed
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class PokemonRepository(
    private val pokemonDao: PokemonDao,
    private val abilityDao: AbilityDao,
    private val moveDao: MoveDao,
    private val typeDao: TypeDao,
    private val pokemonTypeDao: PokemonTypeDao,
    private val pokemonMoveDao: PokemonMoveDao,
    private val pokemonAbilityDao: PokemonAbilityDao,
    private val localDatabase: LocalDatabase,
    private val pokemonWebservice: PokemonWebservice
) {
    fun listenToPokemon(name: String): LiveData<PokemonDetailed?> {
        return pokemonDao.selectPokemonByIdLiveData(name.normalizeToQuery())
    }

    fun listenToFavoritePokemonList(): LiveData<List<DisplayPokemon>> {
        return pokemonDao.getAllFavoritePokemon()
    }

    @ExperimentalPagingApi
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
        generationRelativeOffset: Int = 0,
        pageSize: Int
    ): PagedPokemonList {
        return pokemonWebservice.fetchAllPokemon(generation, generationRelativeOffset, pageSize)
    }

    suspend fun updatePokemon(name: String) {
        val newName = name.normalizeToQuery()

        val hasDetailedPokemon = pokemonDao.hasDetailedPokemon(newName)

        if (!hasDetailedPokemon) {
            fetchPokemonDetails(newName)
        }
    }

    private suspend fun fetchPokemonDetails(name: String) = coroutineScope {
        val pokemonDetailed = pokemonWebservice.searchPokemon(name)

        localDatabase.withTransaction {
            pokemonDao.insertOrUpdatePokemonPreservingFavoriteFlag(pokemonDetailed.pokemon)

            pokemonDetailed.abilities.forEach {
                abilityDao.insert(it)
                pokemonAbilityDao.insert(
                    PokemonAbilityCrossRef(
                        pokemonName = name,
                        abilityId = it.abilityId
                    )
                )
            }

            pokemonDetailed.moves.forEach {
                moveDao.insert(it)
                pokemonMoveDao.insert(
                    PokemonMoveCrossRef(
                        pokemonName = name,
                        moveId = it.moveId
                    )
                )
            }

            pokemonDetailed.types.forEach {
                typeDao.insert(it)
                pokemonTypeDao.insert(
                    PokemonTypeCrossRef(
                        pokemonName = name,
                        typeId = it.typeId
                    )
                )
            }
        }
    }

    suspend fun insertPokemon(vararg pokemon: DisplayPokemon) {
        pokemonDao.insertIfNotPresent(*pokemon)
    }

    suspend fun toggleFavoritePokemon(pokemon: Pokemon) {
        pokemonDao.toggleFavoriteFlag(pokemon)
    }

    suspend fun toggleFavoritePokemon(pokemon: DisplayPokemon) {
        toggleFavoritePokemon(pokemon.pokemonName)
    }

    private suspend fun toggleFavoritePokemon(name: String) {
        pokemonDao.toggleFavoriteFlag(name)
    }
}