package com.lucasrodrigues.pokemonshowcase.data_access.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class RemoteKey(
    @PrimaryKey val pokemonName: String,
    val pokemonNumber: Int,
    val previousKey: Int?,
    val nextKey: Int?,
) : Parcelable