package com.lucasrodrigues.pokemonshowcase.components.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.components.adapter.view_holder.DataBindingViewHolder
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.utils.DisplayPokemonComparator

class PokemonAdapter(
    private val navigationService: NavigationService,
    private val layoutId: Int
) : PagingDataAdapter<DisplayPokemon, DataBindingViewHolder>(DisplayPokemonComparator) {
    private var lastPosition = -1

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item: DisplayPokemon? = getItem(position)

        holder.bind(item) {
            if (item != null)
                navigationService.navigateToPokemonDetails(item.name)
        }

        setAnimation(holder.itemView, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        return DataBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }

    override fun onViewDetachedFromWindow(holder: DataBindingViewHolder) {
        holder.clearAnimation()
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        val animation = AnimationUtils.loadAnimation(
            viewToAnimate.context,
            if (position > lastPosition) R.anim.slide_in_bottom else R.anim.slide_in_top
        )

        viewToAnimate.startAnimation(animation)

        lastPosition = position
    }
}