package com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["pokemonName", "abilityId"])
data class PokemonAbilityCrossRef(
    val pokemonName: String,
    val abilityId: Int
) : Parcelable