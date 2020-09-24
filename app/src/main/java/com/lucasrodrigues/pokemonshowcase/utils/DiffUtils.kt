package com.lucasrodrigues.pokemonshowcase.utils

import androidx.recyclerview.widget.DiffUtil
import com.lucasrodrigues.pokemonshowcase.model.Pokemon

object PokemonComparator : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}