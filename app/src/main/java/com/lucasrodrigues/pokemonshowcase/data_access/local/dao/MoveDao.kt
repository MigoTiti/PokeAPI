package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Move

@Dao
interface MoveDao : BaseDao<Move> {
    @Query("SELECT count(*) > 0 FROM Move WHERE moveId == :id")
    suspend fun hasElement(id: Int): Boolean

    @Query("SELECT * FROM Move WHERE moveId == :id")
    suspend fun fetchById(id: Int): Move?

    @Query("DELETE FROM Move")
    suspend fun clear()
}