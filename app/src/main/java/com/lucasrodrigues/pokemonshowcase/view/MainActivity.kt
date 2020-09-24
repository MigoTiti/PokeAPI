package com.lucasrodrigues.pokemonshowcase.view

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView?)?.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isQueryRefinementEnabled = true

            setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return true
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    val selectedView: CursorAdapter = suggestionsAdapter
                    val cursor: Cursor = selectedView.getItem(position) as Cursor
                    val index: Int = cursor.getColumnIndexOrThrow(
                        SearchManager.SUGGEST_COLUMN_TEXT_1
                    )
                    setQuery(cursor.getString(index), true)
                    return true
                }
            })
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_open_favorites -> navigationService.navigateToFavoritePokemon()
        }

        return super.onOptionsItemSelected(item)
    }
}