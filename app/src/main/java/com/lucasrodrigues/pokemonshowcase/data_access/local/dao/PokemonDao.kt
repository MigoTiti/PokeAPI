package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Pokemon
import com.lucasrodrigues.pokemonshowcase.extensions.toPokemon
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.PokemonDetailed

@Dao
interface PokemonDao : BaseDao<Pokemon> {

    @Query("SELECT pokemonName, number, isFavorite FROM Pokemon WHERE number >= :begin AND number <= :end ORDER BY number ASC")
    fun selectAllDisplayPokemonPagingSource(begin: Int, end: Int): PagingSource<Int, DisplayPokemon>

    @Query("SELECT pokemonName, number, isFavorite FROM Pokemon WHERE isFavorite == 1 ORDER BY number ASC")
    fun getAllFavoritePokemon(): LiveData<List<DisplayPokemon>>

    @Transaction
    @Query("SELECT * FROM Pokemon WHERE pokemonName == :id")
    fun selectPokemonByIdLiveData(id: String): LiveData<PokemonDetailed>

    @Query("SELECT * FROM Pokemon WHERE pokemonName == :nameQuery")
    suspend fun selectPokemonUsingQuery(nameQuery: String): Pokemon?

    @Query("SELECT * FROM Pokemon WHERE pokemonName == :id")
    suspend fun selectPokemonById(id: String): Pokemon?

    @Query("SELECT count(*) > 0 FROM Pokemon WHERE pokemonName == :name AND height != null")
    suspend fun hasDetailedPokemon(name: String): Boolean

    @Transaction
    suspend fun toggleFavoriteFlag(pokemon: Pokemon) {
        update(pokemon.copy(isFavorite = !pokemon.isFavorite))
    }

    @Transaction
    suspend fun toggleFavoriteFlag(name: String) {
        val pokemon = selectPokemonById(name)

        if (pokemon != null)
            update(pokemon.copy(isFavorite = !pokemon.isFavorite))
    }

    @Query("DELETE FROM Pokemon")
    suspend fun clear()

    @Transaction
    suspend fun insertOrUpdatePokemonPreservingFavoriteFlag(vararg pokemon: Pokemon) {
        pokemon.forEach {
            val currentItem = selectPokemonById(it.pokemonName)

            if (currentItem != null) {
                update(it.copy(isFavorite = currentItem.isFavorite))
            } else {
                insert(it)
            }
        }
    }

    @Transaction
    suspend fun insertIfNotPresent(vararg pokemon: DisplayPokemon) {
        pokemon.forEach {
            val currentItem = selectPokemonById(it.pokemonName)

            if (currentItem == null) {
                insert(it.toPokemon())
            }
        }
    }
}