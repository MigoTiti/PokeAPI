package com.lucasrodrigues.pokemonshowcase.data_access.local.dao

import androidx.room.Dao
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonMoveCrossRef

@Dao
interface PokemonMoveDao : BaseDao<PokemonMoveCrossRef>