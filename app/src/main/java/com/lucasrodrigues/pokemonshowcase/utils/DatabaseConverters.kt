package com.lucasrodrigues.pokemonshowcase.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lucasrodrigues.pokemonshowcase.model.PokemonSprite
import com.lucasrodrigues.pokemonshowcase.model.PokemonStat

object DatabaseConverters {

    @JvmStatic
    @TypeConverter
    fun toPokemonSpriteListFromString(value: String?): List<PokemonSprite>? {
        return Gson().fromJson(value, object : TypeToken<List<PokemonSprite>>() {}.type)
    }

    @JvmStatic
    @TypeConverter
    fun toStringFromPokemonSpriteList(list: List<PokemonSprite>?): String {
        return Gson().toJson(list)
    }

    @JvmStatic
    @TypeConverter
    fun toPokemonStatListFromString(value: String?): List<PokemonStat>? {
        return Gson().fromJson(value, object : TypeToken<List<PokemonStat>>() {}.type)
    }

    @JvmStatic
    @TypeConverter
    fun toStringFromPokemonStatList(list: List<PokemonStat>?): String {
        return Gson().toJson(list)
    }
}