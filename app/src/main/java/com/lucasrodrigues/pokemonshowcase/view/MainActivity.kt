package com.lucasrodrigues.pokemonshowcase.view

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.components.adapter.PagingLoadStateAdapter
import com.lucasrodrigues.pokemonshowcase.components.adapter.PokemonAdapter
import com.lucasrodrigues.pokemonshowcase.databinding.ActivityMainBinding
import com.lucasrodrigues.pokemonshowcase.view_model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalPagingApi
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel by viewModel<MainViewModel> { parametersOf(this as Activity) }
    override val layoutId = R.layout.activity_main

    private val allPokemonAdapter = PokemonAdapter(
        navigationService = navigationService,
        layoutId = R.layout.item_pokemon
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allPokemonRv.apply {
            layoutManager = GridLayoutManager(
                context,
                3,
                RecyclerView.VERTICAL,
                false
            )

            adapter = allPokemonAdapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter(
                    loadingLayoutId = R.layout.item_pokemon_placeholder,
                    errorLayoutId = R.layout.item_pokemon_error_loading
                ) {
                    allPokemonAdapter.retry()
                }
            )
        }

        viewModel
            .getAllPokemon()
            .onEach { allPokemonAdapter.submitData(it) }
            .launchIn(viewModel.viewModelScope)
    }
}