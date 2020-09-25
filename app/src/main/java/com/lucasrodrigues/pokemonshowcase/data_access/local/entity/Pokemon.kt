package com.lucasrodrigues.pokemonshowcase.data_access.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.lucasrodrigues.pokemonshowcase.model.PokemonSprite
import com.lucasrodrigues.pokemonshowcase.model.PokemonStat
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
    val baseStats: List<PokemonStat>? = null,
    val height: Double? = null,
    val weight: Double? = null,
    val isFavorite: Boolean = false,
) : Parcelable