package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Type

@Dao
interface TypeDao : BaseDao<Type> {
    @Query("DELETE FROM Type")
    suspend fun clear()
}