package com.lucasrodrigues.pokemonshowcase

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.lucasrodrigues.pokemonshowcase.data_access.local.LocalDatabase
import com.lucasrodrigues.pokemonshowcase.data_access.local.dao.PokemonDao
import com.lucasrodrigues.pokemonshowcase.dependencies.RepositoryDependencies
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import com.lucasrodrigues.pokemonshowcase.webservice.test.PokemonWebserviceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
class PokemonRepositoryUnitTests : KoinTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val pokemonRepository by inject<PokemonWebservice>()
	private val pokemonDao by inject<PokemonDao>()

    @Before
    fun initializeMockedDependencies() {
        startKoin {
            modules(
				listOf(
					RepositoryDependencies.module,
					module {
						single {
							Room.inMemoryDatabaseBuilder(
								ApplicationProvider.getApplicationContext(),
								LocalDatabase::class.java
							).build()
						}
						single { get<LocalDatabase>().pokemonDao() }
						single<PokemonWebservice> { PokemonWebserviceTest() }
					},
				)
			)
        }
    }
}