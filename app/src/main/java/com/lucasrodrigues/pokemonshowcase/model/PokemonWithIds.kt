package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Pokemon
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonWithIds(
    val pokemon: Pokemon,
    val abilitiesIds: List<Int>,
    val typesIds: List<Int>,
    val movesIds: List<Int>
) : Parcelable