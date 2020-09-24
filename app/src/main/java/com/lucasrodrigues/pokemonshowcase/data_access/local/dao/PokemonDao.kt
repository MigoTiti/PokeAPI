package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.Pokemon

@Dao
interface PokemonDao : BaseDao<Pokemon> {

    @Query("SELECT id, name FROM Pokemon")
    fun selectAllDisplayPokemonPagingSource(): PagingSource<Int, DisplayPokemon>

    @Query("SELECT id, name FROM Pokemon WHERE is_favorite == 1")
    fun getAllPokemon(): LiveData<List<DisplayPokemon>>

    @Query("SELECT * FROM Pokemon WHERE name == :nameQuery")
    suspend fun selectPokemonUsingQuery(nameQuery: String): Pokemon?

    @Query("SELECT * FROM Pokemon WHERE id == :id")
    suspend fun selectPokemonById(id: Int): Pokemon?

    @Transaction
    suspend fun toggleFavoriteFlag(pokemon: Pokemon) {
        update(pokemon.copy(isFavorite = !pokemon.isFavorite))
    }

    @Query("DELETE FROM Pokemon")
    suspend fun clear()

    @Transaction
    suspend fun insertPokemonPreservingFavoriteFlag(vararg pokemon: Pokemon) {
        pokemon.forEach {
            val currentItem = selectPokemonById(it.id)

            if (currentItem != null) {
                update(it.copy(isFavorite = currentItem.isFavorite))
            } else {
                insert(it)
            }
        }
    }
}