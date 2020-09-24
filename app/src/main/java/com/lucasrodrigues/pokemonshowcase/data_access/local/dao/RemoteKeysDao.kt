package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucasrodrigues.pokemonshowcase.model.RemoteKey

@Dao
interface RemoteKeysDao : BaseDao<RemoteKey> {
    @Query("SELECT * FROM RemoteKey WHERE pokemonName = :pokemonName")
    suspend fun remoteKeysById(pokemonName: String): RemoteKey?

    @Query("SELECT * FROM RemoteKey WHERE pokemon_number >= :begin AND pokemon_number <= :end ORDER BY pokemon_number DESC LIMIT 1")
    suspend fun lastKey(begin: Int, end: Int): RemoteKey?

    @Query("SELECT * FROM RemoteKey WHERE pokemon_number >= :begin AND pokemon_number <= :end ORDER BY pokemon_number ASC LIMIT 1")
    suspend fun firstKey(begin: Int, end: Int): RemoteKey?

    @Query("DELETE FROM RemoteKey")
    suspend fun clear()
}