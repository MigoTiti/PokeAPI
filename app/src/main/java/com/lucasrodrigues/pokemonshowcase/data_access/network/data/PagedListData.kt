package com.lucasrodrigues.pokemonshowcase.data_access.network.data

data class PagedListData<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)