package com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["pokemonName", "typeId"])
data class PokemonTypeCrossRef(
    val pokemonName: String,
    val typeId: Int
) : Parcelable