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
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.koin.test.inject

@ExperimentalCoroutinesApi
@RunWith(Parameterized::class)
class PokemonWebserviceSearchUnitTests(
    private val queryString: String,
    private val errorQueryString: String,
) : BaseUnitTest() {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "Query string: {0}, error query string: {1}")
        fun data() = listOf(arrayOf("Pokemon 1", "poke"))
    }

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    private val pokemonWebservice by inject<PokemonWebservice>()

    override val modules = listOf(
        DataAccessDependencies.module,
        WebserviceDependencies.module
    )

    @Test
    fun searches_pokemonCorrectly() = runBlockingTest {
        val pokemonResult: PokemonWithIds? = pokemonWebservice.searchPokemon(queryString)

        assertNotNull(pokemonResult)
    }

    @Test
    fun failsToSearchPokemon() = runBlockingTest {
        exceptionRule.expect(Exception::class.java)

        pokemonWebservice.searchPokemon(errorQueryString)
    }
}