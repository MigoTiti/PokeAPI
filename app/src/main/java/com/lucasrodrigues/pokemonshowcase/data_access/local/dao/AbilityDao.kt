package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Ability

@Dao
interface AbilityDao : BaseDao<Ability> {
    @Query("SELECT count(*) > 0 FROM Ability WHERE abilityId == :id")
    suspend fun hasElement(id: Int): Boolean

    @Query("SELECT * FROM Ability WHERE abilityId == :id")
    suspend fun fetchById(id: Int): Ability?

    @Query("DELETE FROM Ability")
    suspend fun clear()
}