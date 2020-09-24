package com.lucasrodrigues.pokemonshowcase.components

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.RemoteKey
import com.lucasrodrigues.pokemonshowcase.repository.PokemonRepository
import com.lucasrodrigues.pokemonshowcase.repository.RemoteKeysRepository
import java.io.InvalidObjectException
import kotlin.math.max

@ExperimentalPagingApi
class PokemonRemoteMediator(
    private val pokemonRepository: PokemonRepository,
    private val remoteKeysRepository: RemoteKeysRepository
) : RemoteMediator<Int, DisplayPokemon>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DisplayPokemon>
    ): MediatorResult {
        val offset = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                max(remoteKeys?.nextKey?.minus(state.config.pageSize) ?: 0, 0)
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                    ?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                val prevKey = remoteKey.previousKey
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )

                prevKey
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)

                if (remoteKey?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }

                remoteKey.nextKey
            }
        }

        try {
            val currentPage = pokemonRepository.fetchPokemonPagedList(
                offset = offset,
                pageSize = state.config.pageSize
            )

            val endOfPaginationReached = when {
                loadType == LoadType.PREPEND && currentPage.previousOffset == null -> true
                loadType == LoadType.APPEND && currentPage.nextOffset == null -> true
                else -> false
            }

            remoteKeysRepository.insertKeys(
                pagedList = currentPage,
                refresh = loadType == LoadType.REFRESH
            )

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, DisplayPokemon>
    ): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
            ?.let { pokemon ->
                remoteKeysRepository.getKeyByPokemonName(pokemon.name)
                    ?: remoteKeysRepository.getLastKey()
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, DisplayPokemon>
    ): RemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()
            ?.let { pokemon ->
                remoteKeysRepository.getKeyByPokemonName(pokemon.name)
                    ?: remoteKeysRepository.getFirstKey()
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, DisplayPokemon>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { pokemon ->
                remoteKeysRepository.getKeyByPokemonName(pokemon.name)
                    ?: remoteKeysRepository.getFirstKey()
            }
        }
    }
}