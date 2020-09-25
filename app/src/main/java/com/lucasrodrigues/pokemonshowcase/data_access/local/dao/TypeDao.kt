package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Type

@Dao
interface TypeDao : BaseDao<Type> {
    @Query("SELECT count(*) > 0 FROM Type WHERE typeId == :id")
    suspend fun hasElement(id: Int): Boolean

    @Query("SELECT * FROM Type WHERE typeId == :id")
    suspend fun fetchById(id: Int): Type?

    @Query("DELETE FROM Type")
    suspend fun clear()
}