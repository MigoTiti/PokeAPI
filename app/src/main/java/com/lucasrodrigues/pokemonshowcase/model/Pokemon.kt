package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    indices = [
        Index(
            value = ["name"],
            unique = true
        )
    ]
)
data class Pokemon(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val abilities: List<String>?,
    @ColumnInfo val sprites: List<PokemonSprite>?,
    @ColumnInfo val height: Int?,
    @ColumnInfo val weight: Int?,
    @ColumnInfo(name = "base_experience") val baseExperience: Int?,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false,
) : Parcelable