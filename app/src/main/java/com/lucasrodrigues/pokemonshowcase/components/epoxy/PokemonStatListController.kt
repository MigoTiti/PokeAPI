package com.lucasrodrigues.pokemonshowcase.components.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.empty
import com.lucasrodrigues.pokemonshowcase.framework.ResourceService
import com.lucasrodrigues.pokemonshowcase.model.PokemonStat
import com.lucasrodrigues.pokemonshowcase.pokemonStat

class PokemonStatListController(
    private val resourceService: ResourceService
) : TypedEpoxyController<List<PokemonStat>>() {
    override fun buildModels(data: List<PokemonStat>?) {
        if (data?.isEmpty() == true)
            empty {
                id("empty")
                item(resourceService.getString(R.string.empty_stat_list))
            }
        else
            data?.forEach { stat ->
                pokemonStat {
                    id(stat.name)
                    item(stat)
                }
            }
    }
}