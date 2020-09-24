package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PagedPokemonList(
    val previousOffset: Int?,
    val nextOffset: Int?,
    val pokemon: List<DisplayPokemon>
) : Parcelable