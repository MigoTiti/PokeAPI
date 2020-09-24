package com.lucasrodrigues.pokemonshowcase.view

import android.app.Activity
import android.os.Bundle
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.databinding.ActivityPokemonDetailsBinding
import com.lucasrodrigues.pokemonshowcase.view_model.PokemonDetailsViewModel
import kotlinx.android.synthetic.main.component_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalPagingApi
class PokemonDetailsActivity :
    BaseActivity<ActivityPokemonDetailsBinding, PokemonDetailsViewModel>() {

    override val viewModel by viewModel<PokemonDetailsViewModel> {
        parametersOf(
            this as Activity,
            intent.getStringExtra("pokemonName")
        )
    }

    override val layoutId = R.layout.activity_pokemon_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tryAgain.setOnClickListener {
            viewModel.fetchPokemon()
        }

        viewModel.fetchPokemon()
    }
}