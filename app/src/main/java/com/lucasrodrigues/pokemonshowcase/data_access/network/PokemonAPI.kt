package com.lucasrodrigues.pokemonshowcase.data_access.network

import com.lucasrodrigues.pokemonshowcase.data_access.network.data.DisplayItemData
import com.lucasrodrigues.pokemonshowcase.data_access.network.data.PagedListData
import com.lucasrodrigues.pokemonshowcase.data_access.network.data.PokemonData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") pageSize: Int
    ): PagedListData<DisplayItemData>

    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetails(
        @Path("id") id: Int
    ): PokemonData

    @GET("pokemon/{name}")
    suspend fun fetchPokemonDetails(
        @Path("name") name: String
    ): PokemonData
}