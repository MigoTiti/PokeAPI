package com.lucasrodrigues.pokemonshowcase.constants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Generation(
    private val begin: Int,
    private val end: Int
) : Parcelable {
    I(1, 151),
    II(152, 251),
    III(252, 386),
    IV(387, 493),
    V(494, 649),
    VI(650, 721),
    VII(722, 809),
    VIII(810, 893);

    fun lowerBound() = begin
    fun upperBound() = end
    fun size() = end - begin + 1
}