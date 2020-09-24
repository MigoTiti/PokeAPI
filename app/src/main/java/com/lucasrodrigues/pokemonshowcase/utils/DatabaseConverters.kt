package com.lucasrodrigues.pokemonshowcase.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lucasrodrigues.pokemonshowcase.model.PokemonSprite
import java.util.*

object DatabaseConverters {

    @JvmStatic
    @TypeConverter
    fun toPokemonSpriteFromString(json: String?): PokemonSprite? {
        return Gson().fromJson(json, PokemonSprite::class.java)
    }

    @JvmStatic
    @TypeConverter
    fun fromPokemonSpriteToSprint(pokemonSprite: PokemonSprite?): String? {
        return Gson().toJson(pokemonSprite)
    }
}