package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DisplayPokemon(
    val name: String,
    val number: Int,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
) : Parcelable