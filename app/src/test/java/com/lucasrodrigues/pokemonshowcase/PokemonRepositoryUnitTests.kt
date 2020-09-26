package com.lucasrodrigues.pokemonshowcase

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.lucasrodrigues.pokemonshowcase.data_access.local.LocalDatabase
import com.lucasrodrigues.pokemonshowcase.data_access.local.dao.PokemonDao
import com.lucasrodrigues.pokemonshowcase.dependencies.RepositoryDependencies
import com.lucasrodrigues.pokemonshowcase.dependencies.WebserviceDependencies
import com.lucasrodrigues.pokemonshowcase.repository.PokemonRepository
import com.lucasrodrigues.pokemonshowcase.webservice.PokemonWebservice
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module
import org.koin.test.inject

@ExperimentalCoroutinesApi
class PokemonRepositoryUnitTests : BaseUnitTest() {

	override val modules = listOf(
		RepositoryDependencies.module,
		WebserviceDependencies.module,
		module {
			single {
				Room.inMemoryDatabaseBuilder(
					ApplicationProvider.getApplicationContext(),
					LocalDatabase::class.java
				).build()
			}
			single { get<LocalDatabase>().pokemonDao() }
		},
	)

    private val pokemonRepository by inject<PokemonRepository>()
	private val pokemonWebservice by inject<PokemonWebservice>()
	private val pokemonDao by inject<PokemonDao>()
}