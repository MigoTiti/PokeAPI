package com.lucasrodrigues.pokemonshowcase.components.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.paging.ExperimentalPagingApi
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.view.fragment.GenerationPokemonFragment

@ExperimentalPagingApi
class GenerationPokemonAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = Generation.values().size

    override fun createFragment(position: Int): Fragment {
        return GenerationPokemonFragment.newInstance(Generation.values()[position])
    }
}