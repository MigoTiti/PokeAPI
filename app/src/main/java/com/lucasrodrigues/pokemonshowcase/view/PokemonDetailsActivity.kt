package com.lucasrodrigues.pokemonshowcase.view

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuItem
import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.components.SuggestionProvider
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
            if (Intent.ACTION_SEARCH == intent.action) {
                intent.getStringExtra(SearchManager.QUERY)
            } else {
                intent.getStringExtra("pokemonName")
            }
        )
    }

    override val layoutId = R.layout.activity_pokemon_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(
                    this,
                    SuggestionProvider.AUTHORITY,
                    SuggestionProvider.MODE
                )
                    .saveRecentQuery(query, null)
            }
        }

        tryAgain.setOnClickListener {
            viewModel.fetchPokemon()
        }

        viewModel.fetchPokemon()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.pokemonDetails.observe(this) {
            title = it?.name?.capitalize() ?: "Buscando '${viewModel.pokemonName}'"

            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.pokemon_details_menu, menu)

        val pokemon = viewModel.pokemonDetails.value

        val item = menu?.findItem(R.id.action_toggle_favorite)

        if (pokemon != null) {
            item?.setIcon(
                if (pokemon.isFavorite)
                    R.drawable.ic_favorite
                else
                    R.drawable.ic_not_favorite
            )
        }

        item?.isVisible = pokemon != null
        item?.isEnabled = pokemon != null

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_toggle_favorite -> viewModel.toggleFavorite()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}