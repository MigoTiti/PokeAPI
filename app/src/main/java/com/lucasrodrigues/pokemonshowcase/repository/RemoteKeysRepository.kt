package com.lucasrodrigues.pokemonshowcase.repository

import androidx.paging.ExperimentalPagingApi
import androidx.room.withTransaction
import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.data_access.local.LocalDatabase
import com.lucasrodrigues.pokemonshowcase.data_access.local.dao.RemoteKeysDao
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.RemoteKey
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList

@ExperimentalPagingApi
class RemoteKeysRepository(
    private val database: LocalDatabase,
    private val remoteKeysDao: RemoteKeysDao,
    private val pokemonRepository: PokemonRepository
) {

    suspend fun insertKeys(pagedList: PagedPokemonList) {
        database.withTransaction {
            val keys = pagedList.pokemon.map {
                RemoteKey(
                    pokemonName = it.pokemonName,
                    pokemonNumber = it.number,
                    previousKey = pagedList.previousOffset,
                    nextKey = pagedList.nextOffset
                )
            }

            remoteKeysDao.insertAll(*keys.toTypedArray())
            pokemonRepository.insertPokemon(*pagedList.pokemon.toTypedArray())
        }
    }

    suspend fun getKeyByPokemonName(name: String): RemoteKey? {
        return remoteKeysDao.remoteKeysById(name)
    }

    suspend fun getLastKey(generation: Generation): RemoteKey? {
        return remoteKeysDao.lastKey(
            begin = generation.lowerBound(),
            end = generation.upperBound()
        )
    }

    suspend fun getFirstKey(generation: Generation): RemoteKey? {
        return remoteKeysDao.firstKey(
            begin = generation.lowerBound(),
            end = generation.upperBound()
        )
    }
}