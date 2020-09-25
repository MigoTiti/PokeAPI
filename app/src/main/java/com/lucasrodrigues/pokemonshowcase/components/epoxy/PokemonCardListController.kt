package com.lucasrodrigues.pokemonshowcase.components.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.lucasrodrigues.pokemonshowcase.empty
import com.lucasrodrigues.pokemonshowcase.framework.ResourceService
import com.lucasrodrigues.pokemonshowcase.pokemonCard

class PokemonCardListController<T>(
    private val resourceService: ResourceService,
    private val emptyListResourceId: Int,
    private val getItemId: (item: T) -> Int,
    private val getItemString: (item: T) -> String
) : TypedEpoxyController<List<T>>() {
    override fun buildModels(data: List<T>?) {
        if (data?.isEmpty() == true)
            empty {
                id("empty")
                item(resourceService.getString(emptyListResourceId))
            }
        else
            data?.forEach { item ->
                pokemonCard {
                    id(getItemId(item))
                    item(getItemString(item))
                }
            }
    }
}