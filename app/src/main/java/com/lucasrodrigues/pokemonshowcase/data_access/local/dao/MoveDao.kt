package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Move

@Dao
interface MoveDao : BaseDao<Move> {
    @Query("DELETE FROM Move")
    suspend fun clear()
}