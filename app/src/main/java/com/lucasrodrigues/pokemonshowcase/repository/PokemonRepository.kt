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
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Ability
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Move
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Pokemon
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Type
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonAbilityCrossRef
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonMoveCrossRef
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonTypeCrossRef
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.PokemonDetailed
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
    fun listenToPokemon(name: String): LiveData<PokemonDetailed> {
        return pokemonDao.selectPokemonByIdLiveData(name)
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
        val hasDetailedPokemon = pokemonDao.hasDetailedPokemon(name)

        if (!hasDetailedPokemon) {
            fetchPokemonDetails(name)
        }
    }

    private suspend fun fetchAbility(id: Int): Ability {
        val currentItem = abilityDao.fetchById(id)

        if (currentItem != null)
            return currentItem

        val newItem = pokemonWebservice.fetchAbility(id)

        abilityDao.insert(newItem)

        return newItem
    }

    private suspend fun fetchMove(id: Int): Move {
        val currentItem = moveDao.fetchById(id)

        if (currentItem != null)
            return currentItem

        val newItem = pokemonWebservice.fetchMove(id)

        moveDao.insert(newItem)

        return newItem
    }

    private suspend fun fetchType(id: Int): Type {
        val currentItem = typeDao.fetchById(id)

        if (currentItem != null)
            return currentItem

        val newItem = pokemonWebservice.fetchType(id)

        typeDao.insert(newItem)

        return newItem
    }

    private suspend fun fetchPokemonDetails(name: String) = coroutineScope {
        val pokemonWithIds = pokemonWebservice.searchPokemon(name)

        val details = awaitAll(
            *pokemonWithIds.abilitiesIds.map {
                async { fetchAbility(it) }
            }.toTypedArray(),
            *pokemonWithIds.movesIds.map {
                async { fetchMove(it) }
            }.toTypedArray(),
            *pokemonWithIds.typesIds.map {
                async { fetchType(it) }
            }.toTypedArray(),
        )

        localDatabase.withTransaction {
            pokemonDao.insertOrUpdatePokemonPreservingFavoriteFlag(pokemonWithIds.pokemon)

            details.forEach {
                when (it) {
                    is Ability -> {
                        pokemonAbilityDao.insert(
                            PokemonAbilityCrossRef(
                                pokemonName = name,
                                abilityId = it.abilityId
                            )
                        )
                    }
                    is Move -> {
                        pokemonMoveDao.insert(
                            PokemonMoveCrossRef(
                                pokemonName = name,
                                moveId = it.moveId
                            )
                        )
                    }
                    is Type -> {
                        pokemonTypeDao.insert(
                            PokemonTypeCrossRef(
                                pokemonName = name,
                                typeId = it.typeId
                            )
                        )
                    }
                }
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