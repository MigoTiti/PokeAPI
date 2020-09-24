package com.lucasrodrigues.pokemonshowcase.framework

interface ResourceService {
    fun getString(id: Int, vararg args: Any): String
    fun getErrorMessage(exception: Throwable): String
    fun getColor(id: Int): Int
}
