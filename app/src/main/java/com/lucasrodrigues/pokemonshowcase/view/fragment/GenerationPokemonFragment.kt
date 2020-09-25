package com.lucasrodrigues.pokemonshowcase.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.components.adapter.PagingLoadStateAdapter
import com.lucasrodrigues.pokemonshowcase.components.adapter.PokemonAdapter
import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.databinding.FragmentGenerationPokemonBinding
import com.lucasrodrigues.pokemonshowcase.model.LoadingState
import com.lucasrodrigues.pokemonshowcase.view_model.GenerationPokemonViewModel
import kotlinx.android.synthetic.main.component_error.*
import kotlinx.android.synthetic.main.fragment_generation_pokemon.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GenerationPokemonFragment :
    BaseFragment<FragmentGenerationPokemonBinding, GenerationPokemonViewModel>() {

    override val viewModel by viewModel<GenerationPokemonViewModel> {
        parametersOf(activity as Activity, generation)
    }

    override val layoutId = R.layout.fragment_generation_pokemon

    lateinit var generation: Generation

    private val allPokemonAdapter by lazy {
        PokemonAdapter(
            navigationService = navigationService,
            layoutId = R.layout.item_pokemon
        ) {
            viewModel.toggleFavorite(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        generation = arguments!!.getParcelable("generation")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        tryAgain.setOnClickListener {
            allPokemonAdapter.retry()
        }
    }

    @ExperimentalPagingApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        allPokemonAdapter.addLoadStateListener { loadState ->
            val refreshState = when (loadState.refresh) {
                is LoadState.Error -> LoadingState.Error((loadState.refresh as LoadState.Error).error)
                is LoadState.Loading -> LoadingState.Loading
                is LoadState.NotLoading -> LoadingState.Idle
            }

            if (refreshState != viewModel.firstLoadState.value)
                viewModel.firstLoadState.postValue(refreshState)
        }

        viewModel
            .getAllPokemon()
            .onEach { allPokemonAdapter.submitData(it) }
            .launchIn(viewModel.viewModelScope)
    }

    companion object {
        fun newInstance(generation: Generation): GenerationPokemonFragment {
            return GenerationPokemonFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("generation", generation)
                }
            }
        }
    }
}