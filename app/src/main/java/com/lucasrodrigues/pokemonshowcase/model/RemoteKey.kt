package com.lucasrodrigues.pokemonshowcase.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class RemoteKey(
    @PrimaryKey val pokemonName: String,
    @ColumnInfo(name = "pokemon_number") val pokemonNumber: Int,
    @ColumnInfo(name = "previous_key") val previousKey: Int?,
    @ColumnInfo(name = "next_key") val nextKey: Int?,
) : Parcelable