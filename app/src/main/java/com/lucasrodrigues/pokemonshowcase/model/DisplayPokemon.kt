package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DisplayPokemon(
    val name: String,
    val number: Int
) : Parcelable