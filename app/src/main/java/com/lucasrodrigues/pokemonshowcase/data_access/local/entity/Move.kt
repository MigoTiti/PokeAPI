package com.lucasrodrigues.pokemonshowcase.data_access.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Move(
    @PrimaryKey val moveId: Int,
    val name: String
) : Parcelable