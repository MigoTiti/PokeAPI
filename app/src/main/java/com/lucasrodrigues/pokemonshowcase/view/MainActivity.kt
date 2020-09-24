package com.lucasrodrigues.pokemonshowcase.view

import android.app.Activity
import android.os.Bundle
import androidx.paging.ExperimentalPagingApi
import com.google.android.material.tabs.TabLayoutMediator
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.components.adapter.GenerationPokemonAdapter
import com.lucasrodrigues.pokemonshowcase.databinding.ActivityMainBinding
import com.lucasrodrigues.pokemonshowcase.view_model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalPagingApi
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel by viewModel<MainViewModel> { parametersOf(this as Activity) }
    override val layoutId = R.layout.activity_main

    private lateinit var generationAdapter: GenerationPokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        generationAdapter = GenerationPokemonAdapter(this)

        generationPager.adapter = generationAdapter
        generationPager.isUserInputEnabled = false

        TabLayoutMediator(generationTabLayout, generationPager) { tab, position ->
            tab.text = "Generation ${position + 1}"
        }.attach()
    }
}