package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DisplayPokemon(
    val pokemonName: String,
    val number: Int,
    val isFavorite: Boolean
) : Parcelable