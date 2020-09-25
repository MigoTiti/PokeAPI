package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Ability
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Move
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Pokemon
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Type
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonAbilityCrossRef
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonMoveCrossRef
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonTypeCrossRef
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonDetailed(
    @Embedded val pokemon: Pokemon,
    @Relation(
        parentColumn = "pokemonName",
        entityColumn = "abilityId",
        associateBy = Junction(PokemonAbilityCrossRef::class)
    )
    val abilities: List<Ability>,
    @Relation(
        parentColumn = "pokemonName",
        entityColumn = "moveId",
        associateBy = Junction(PokemonMoveCrossRef::class)
    )
    val moves: List<Move>,
    @Relation(
        parentColumn = "pokemonName",
        entityColumn = "typeId",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val types: List<Type>
) : Parcelable