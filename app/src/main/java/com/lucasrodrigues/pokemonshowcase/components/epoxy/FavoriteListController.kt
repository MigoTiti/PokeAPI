package com.lucasrodrigues.pokemonshowcase.components.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.empty
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService
import com.lucasrodrigues.pokemonshowcase.framework.ResourceService
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.pokemon

class FavoriteListController(
    private val navigationService: NavigationService,
    private val resourceService: ResourceService,
    private val onFavoriteClick: (pokemon: DisplayPokemon) -> Unit
) : TypedEpoxyController<List<DisplayPokemon>>() {
    override fun buildModels(data: List<DisplayPokemon>?) {
        if (data?.isEmpty() == true)
            empty {
                id("empty")
                item(resourceService.getString(R.string.empty_favorite_list))
            }
        else
            data?.forEach { pokemon ->
                pokemon {
                    id(pokemon.pokemonName)
                    item(pokemon)
                    onClick { _ ->
                        navigationService.navigateToPokemonDetails(pokemon.pokemonName)
                    }
                    onFavoriteClick { _ ->
                        onFavoriteClick(pokemon)
                    }
                }
            }
    }
}