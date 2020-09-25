package com.lucasrodrigues.pokemonshowcase.view

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.components.epoxy.FavoriteListController
import com.lucasrodrigues.pokemonshowcase.databinding.ActivityFavoritePokemonBinding
import com.lucasrodrigues.pokemonshowcase.view_model.FavoritePokemonViewModel
import kotlinx.android.synthetic.main.activity_favorite_pokemon.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavoritePokemonActivity :
    BaseActivity<ActivityFavoritePokemonBinding, FavoritePokemonViewModel>() {

    override val viewModel by viewModel<FavoritePokemonViewModel> { parametersOf(this as Activity) }

    override val layoutId = R.layout.activity_favorite_pokemon

    private val favoriteListController = FavoriteListController(
        navigationService,
        resourceService
    ) {
        viewModel.toggleFavorite(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoritePokemonRv.apply {
            setController(favoriteListController)

            layoutManager = GridLayoutManager(
                this@FavoritePokemonActivity,
                3,
                RecyclerView.VERTICAL,
                false
            )
        }

        viewModel.favoritePokemon.observe(this) {
            if (it.isNotEmpty()) {
                favoritePokemonRv.layoutManager = GridLayoutManager(
                    this,
                    3,
                    RecyclerView.VERTICAL,
                    false
                )
            } else {
                favoritePokemonRv.layoutManager = LinearLayoutManager(this)
            }

            favoriteListController.setData(it)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}