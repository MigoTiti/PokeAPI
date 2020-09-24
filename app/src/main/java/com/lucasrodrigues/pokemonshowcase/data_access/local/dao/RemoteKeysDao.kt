package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucasrodrigues.pokemonshowcase.model.RemoteKey

@Dao
interface RemoteKeysDao : BaseDao<RemoteKey> {
    @Query("SELECT * FROM RemoteKey WHERE pokemonName = :pokemonName")
    suspend fun remoteKeysById(pokemonName: String): RemoteKey?

    @Query("SELECT * FROM RemoteKey ORDER BY pokemon_number DESC LIMIT 1")
    suspend fun lastKey(): RemoteKey?

    @Query("SELECT * FROM RemoteKey ORDER BY pokemon_number ASC LIMIT 1")
    suspend fun firstKey(): RemoteKey?

    @Query("DELETE FROM RemoteKey")
    suspend fun clear()
}