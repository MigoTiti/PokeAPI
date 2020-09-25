package com.lucasrodrigues.pokemonshowcase

import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Pokemon
import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import com.lucasrodrigues.pokemonshowcase.webservice.test.PokemonWebserviceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


@ExperimentalCoroutinesApi
class PokemonWebserviceTestUnitTests : KoinTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    private val pokemonWebservice by inject<PokemonWebservice>()

    @Before
    fun initializeMockedDependencies() {
        startKoin {
            modules(
				listOf(
					module {
						single<PokemonWebservice> { PokemonWebserviceTest() }
					},
				)
			)
        }
    }

    @Test
    fun fetches_singleGenerationProgressivelyCorrectly() = runBlockingTest {
        val generation = Generation.II
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

    @Test
    fun fetches_allGenerationsCorrectly() = runBlockingTest {
        val requests = Generation.values().map {
            async { fetchGeneration(it) }
        }

        val pokemonGenerations = awaitAll(*requests.toTypedArray())

        pokemonGenerations.forEachIndexed { index, pagedPokemonList ->
            val generation = Generation.values()[index]

            assertEquals(null, pagedPokemonList.previousOffset)
            assertEquals(null, pagedPokemonList.nextOffset)
            assertEquals(generation.size(), pagedPokemonList.pokemon.size)
        }
    }

    private suspend fun fetchGeneration(generation: Generation): PagedPokemonList {
        return pokemonWebservice.fetchAllPokemon(
            generation = generation,
            generationRelativeOffset = 0,
            pageSize = generation.size()
        )
    }

    @Test
    fun searches_pokemonCorrectly() = runBlockingTest {
        val pokemonResult: Pokemon? = pokemonWebservice.searchPokemon("pokemon1")

        assertNotNull(pokemonResult)
    }

    @Test
    fun failsToSearchPokemon() = runBlockingTest {
        exceptionRule.expect(Exception::class.java)
        exceptionRule.expectMessage("Pokemon não encontrado")

        pokemonWebservice.searchPokemon("poke")
    }
}