package com.lucasrodrigues.pokemonshowcase

import com.lucasrodrigues.pokemonshowcase.dependencies.DataAccessDependencies
import com.lucasrodrigues.pokemonshowcase.dependencies.WebserviceDependencies
import com.lucasrodrigues.pokemonshowcase.model.PokemonWithIds
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.koin.test.inject

@ExperimentalCoroutinesApi
class PokemonWebserviceSearchUnitTests : BaseUnitTest() {

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    private val pokemonWebservice by inject<PokemonWebservice>()

    override val modules = listOf(
        DataAccessDependencies.module,
        WebserviceDependencies.module
    )

    @Test
    fun searches_pokemonCorrectly() = runBlockingTest {
        val pokemonResult: PokemonWithIds? = pokemonWebservice.searchPokemon("Pokemon 1")

        assertNotNull(pokemonResult)
    }

    @Test
    fun failsToSearchPokemon() = runBlockingTest {
        exceptionRule.expect(Exception::class.java)

        pokemonWebservice.searchPokemon("poke")
    }
}