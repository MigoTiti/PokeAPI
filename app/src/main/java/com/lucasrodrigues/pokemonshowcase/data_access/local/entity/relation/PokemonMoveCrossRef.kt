package com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    primaryKeys = ["pokemonName", "moveId"],
    indices = [Index(value = ["moveId"])]
)
data class PokemonMoveCrossRef(
    val pokemonName: String,
    val moveId: Int
) : Parcelable