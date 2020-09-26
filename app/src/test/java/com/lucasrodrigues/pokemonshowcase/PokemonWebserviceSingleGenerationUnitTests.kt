package com.lucasrodrigues.pokemonshowcase

import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.dependencies.DataAccessDependencies
import com.lucasrodrigues.pokemonshowcase.dependencies.WebserviceDependencies
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.koin.test.inject

@RunWith(Parameterized::class)
class PokemonWebserviceSingleGenerationUnitTests(
    private val generation: Generation
) : BaseUnitTest() {

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    private val pokemonWebservice by inject<PokemonWebservice>()

    override val modules = listOf(
        DataAccessDependencies.module,
        WebserviceDependencies.module
    )

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "Generation {0}")
        fun data() = Generation.values().toList()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetches_generationProgressivelyCorrectly() = runBlockingTest {
        val pageSize = 20

        var currentPage = pokemonWebservice.fetchAllPokemon(
            generation = generation,
            pageSize = pageSize,
            generationRelativeOffset = 0
        )

        val pokemonList = currentPage.pokemon.toMutableList()

        while (currentPage.nextOffset != null) {
            currentPage = pokemonWebservice.fetchAllPokemon(
                generation = generation,
                pageSize = pageSize,
                generationRelativeOffset = currentPage.nextOffset!!
            )

            pokemonList.addAll(currentPage.pokemon)
        }

        assertEquals(null, currentPage.nextOffset)
        assertEquals(generation.size(), pokemonList.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetches_completeGenerationCorrectly() = runBlockingTest {
        val pagedPokemonList = pokemonWebservice.fetchAllPokemon(
            generation = generation,
            generationRelativeOffset = 0,
            pageSize = generation.size()
        )

        assertEquals(null, pagedPokemonList.previousOffset)
        assertEquals(null, pagedPokemonList.nextOffset)
        assertEquals(generation.size(), pagedPokemonList.pokemon.size)
    }
}