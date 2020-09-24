package com.lucasrodrigues.pokemonshowcase.utils

import androidx.recyclerview.widget.DiffUtil
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon

object DisplayPokemonComparator : DiffUtil.ItemCallback<DisplayPokemon>() {
    override fun areItemsTheSame(oldItem: DisplayPokemon, newItem: DisplayPokemon): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: DisplayPokemon, newItem: DisplayPokemon): Boolean {
        return oldItem == newItem
    }
}