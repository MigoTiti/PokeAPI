package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lucasrodrigues.pokemonshowcase.extensions.toPokemon
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.Pokemon

@Dao
interface PokemonDao : BaseDao<Pokemon> {

    @Query("SELECT name, number, is_favorite FROM Pokemon WHERE number >= :begin AND number <= :end ORDER BY number ASC")
    fun selectAllDisplayPokemonPagingSource(begin: Int, end: Int): PagingSource<Int, DisplayPokemon>

    @Query("SELECT name, number, is_favorite FROM Pokemon WHERE is_favorite == 1 ORDER BY number ASC")
    fun getAllFavoritePokemon(): LiveData<List<DisplayPokemon>>

    @Query("SELECT * FROM Pokemon WHERE name == :id")
    fun selectPokemonByIdLiveData(id: String): LiveData<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE name == :nameQuery")
    suspend fun selectPokemonUsingQuery(nameQuery: String): Pokemon?

    @Query("SELECT * FROM Pokemon WHERE name == :id")
    suspend fun selectPokemonById(id: String): Pokemon?

    @Query("SELECT count(*) > 0 FROM Pokemon WHERE name == :name AND abilities != null")
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
            val currentItem = selectPokemonById(it.name)

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
            val currentItem = selectPokemonById(it.name)

            if (currentItem == null) {
                insert(it.toPokemon())
            }
        }
    }
}