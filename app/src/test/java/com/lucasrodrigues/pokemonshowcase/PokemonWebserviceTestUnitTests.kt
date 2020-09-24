package com.lucasrodrigues.pokemonshowcase

import com.lucasrodrigues.pokemonshowcase.model.PagedPokemonList
import com.lucasrodrigues.pokemonshowcase.model.Pokemon
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import com.lucasrodrigues.pokemonshowcase.webservice.test.PokemonWebserviceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    fun fetches_pokemonListFirstPageCorrectly() = runBlockingTest {
        val pageSize = 20

        val pagedList: PagedPokemonList = pokemonWebservice.fetchAllPokemon(
			offset = 0,
			pageSize = pageSize
		)

        assertEquals(null, pagedList.previousOffset)
        assertEquals(pageSize, pagedList.nextOffset)
        assertEquals(pageSize, pagedList.pokemon.size)
    }

    @Test
    fun fetches_pokemonListLastPageCorrectly() = runBlockingTest {
        val pagedList: PagedPokemonList = pokemonWebservice.fetchAllPokemon(
			offset = 140,
			pageSize = 20
		)

        assertEquals(120, pagedList.previousOffset)
        assertEquals(null, pagedList.nextOffset)
        assertEquals(11, pagedList.pokemon.size)
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