package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Ability

@Dao
interface AbilityDao : BaseDao<Ability> {
    @Query("DELETE FROM Ability")
    suspend fun clear()
}