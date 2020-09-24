package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonSprite(
    val name: String,
    val url: String,
) : Parcelable