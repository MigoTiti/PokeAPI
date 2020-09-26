package com.lucasrodrigues.pokemonshowcase.view

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.components.SuggestionProvider
import com.lucasrodrigues.pokemonshowcase.components.epoxy.PokemonCardListController
import com.lucasrodrigues.pokemonshowcase.components.epoxy.PokemonSpriteListController
import com.lucasrodrigues.pokemonshowcase.components.epoxy.PokemonStatListController
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Ability
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Move
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Type
import com.lucasrodrigues.pokemonshowcase.databinding.ActivityPokemonDetailsBinding
import com.lucasrodrigues.pokemonshowcase.model.LoadingState
import com.lucasrodrigues.pokemonshowcase.view_model.PokemonDetailsViewModel
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import kotlinx.android.synthetic.main.component_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

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

    private val spriteController = PokemonSpriteListController(resourceService)
    private val statController = PokemonStatListController(resourceService)

    private val typeController = PokemonCardListController<Type>(
        resourceService = resourceService,
        emptyListResourceId = R.string.empty_type_list,
        { it.typeId },
        { it.name }
    )

    private val moveController = PokemonCardListController<Move>(
        resourceService = resourceService,
        emptyListResourceId = R.string.empty_move_list,
        { it.moveId },
        { it.name }
    )

    private val abilityController = PokemonCardListController<Ability>(
        resourceService = resourceService,
        emptyListResourceId = R.string.empty_ability_list,
        { it.abilityId },
        { it.name }
    )

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
            title = it?.pokemon?.pokemonName?.capitalize() ?: "Buscando '${viewModel.pokemonName}'"

            invalidateOptionsMenu()

            spriteController.setData(it.pokemon.sprites)
            typeController.setData(it.types)
            abilityController.setData(it.abilities)
            moveController.setData(it.moves)
            statController.setData(it.pokemon.baseStats)
        }

        pokemonSpriteRv.apply {
            setController(spriteController)
        }

        pokemonAbilityRv.apply {
            layoutManager = object : FlexboxLayoutManager(
                context,
                FlexDirection.ROW
            ) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            setController(abilityController)
        }

        pokemonMoveRv.apply {
            layoutManager = object : FlexboxLayoutManager(
                context,
                FlexDirection.ROW
            ) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            setController(moveController)
        }

        pokemonStatRv.apply {
            layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            setController(statController)
        }

        pokemonTypeRv.apply {
            layoutManager = object : FlexboxLayoutManager(
                context,
                FlexDirection.ROW
            ) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            setController(typeController)
        }

        viewModel.fetchingPokemonState.observe(this) {
            if (it is LoadingState.Idle)
                motionLayout.transitionToEnd()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.pokemon_details_menu, menu)

        val pokemon = viewModel.pokemonDetails.value?.pokemon

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