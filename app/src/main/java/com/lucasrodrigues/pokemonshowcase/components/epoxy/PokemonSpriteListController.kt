package com.lucasrodrigues.pokemonshowcase.components.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.empty
import com.lucasrodrigues.pokemonshowcase.framework.ResourceService
import com.lucasrodrigues.pokemonshowcase.model.PokemonSprite
import com.lucasrodrigues.pokemonshowcase.pokemonSprite

class PokemonSpriteListController(
    private val resourceService: ResourceService
) : TypedEpoxyController<List<PokemonSprite>>() {
    override fun buildModels(data: List<PokemonSprite>?) {
        if (data?.isEmpty() == true)
            empty {
                id("empty")
                item(resourceService.getString(R.string.empty_sprite_list))
            }
        else
            data?.forEach { sprite ->
                pokemonSprite {
                    id(sprite.name)
                    item(sprite)
                }
            }
    }
}