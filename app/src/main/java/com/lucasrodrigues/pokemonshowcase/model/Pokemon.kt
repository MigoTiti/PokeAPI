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
            value = ["number"],
            unique = true
        )
    ]
)
data class Pokemon(
    @PrimaryKey val name: String,
    @ColumnInfo val number: Int,
    @ColumnInfo val abilities: List<String>? = null,
    @ColumnInfo val sprites: List<PokemonSprite>? = null,
    @ColumnInfo val height: Int? = null,
    @ColumnInfo val weight: Int? = null,
    @ColumnInfo(name = "base_experience") val baseExperience: Int? = null,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false,
) : Parcelable