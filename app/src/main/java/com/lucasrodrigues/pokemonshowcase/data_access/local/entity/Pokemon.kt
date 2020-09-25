package com.lucasrodrigues.pokemonshowcase.data_access.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.lucasrodrigues.pokemonshowcase.model.PokemonSprite
import kotlinx.android.parcel.Parcelize

@Entity(
    indices = [
        Index(
            value = ["number"],
            unique = true
        )
    ]
)
@Parcelize
data class Pokemon(
    @PrimaryKey val pokemonName: String,
    val number: Int,
    val sprites: List<PokemonSprite>? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val baseExperience: Int? = null,
    val isFavorite: Boolean = false,
) : Parcelable