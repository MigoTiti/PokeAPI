package com.lucasrodrigues.pokemonshowcase.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lucasrodrigues.pokemonshowcase.model.PokemonSprite

object DatabaseConverters {

    @JvmStatic
    @TypeConverter
    fun toPokemonSpriteListFromString(value: String?): List<PokemonSprite>? {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @JvmStatic
    @TypeConverter
    fun toStringFromPokemonSpriteList(list: List<PokemonSprite>?): String {
        return Gson().toJson(list)
    }

    @JvmStatic
    @TypeConverter
    fun toStringListFromString(value: String?): List<String>? {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @JvmStatic
    @TypeConverter
    fun toStringFromStringList(list: List<String>?): String {
        return Gson().toJson(list)
    }
}