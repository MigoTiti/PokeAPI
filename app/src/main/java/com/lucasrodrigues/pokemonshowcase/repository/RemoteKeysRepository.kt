package com.lucasrodrigues.pokemonshowcase.repository

import androidx.paging.ExperimentalPagingApi
import androidx.room.withTransaction
import com.lucasrodrigues.pokemonshowcase.data_access.local.LocalDatabase
import com.lucasrodrigues.pokemonshowcase.data_access.local.dao.RemoteKeysDao
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.RemoteKey

@ExperimentalPagingApi
class RemoteKeysRepository(
    private val database: LocalDatabase,
    private val remoteKeysDao: RemoteKeysDao,
    private val pokemonRepository: PokemonRepository
) {

    suspend fun insertKeys(pagedList: PagedPokemonList, refresh: Boolean = false) {
        database.withTransaction {
            if (refresh) {
                remoteKeysDao.clear()
            }

            val keys = pagedList.pokemon.map {
                RemoteKey(
                    pokemonName = it.name,
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

    suspend fun getLastKey(): RemoteKey? {
        return remoteKeysDao.lastKey()
    }

    suspend fun getFirstKey(): RemoteKey? {
        return remoteKeysDao.firstKey()
    }
}