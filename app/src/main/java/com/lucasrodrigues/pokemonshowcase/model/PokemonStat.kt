package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonStat(
    val name: String,
    val value: Int
) : Parcelable