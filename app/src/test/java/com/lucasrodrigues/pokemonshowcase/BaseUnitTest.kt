package com.lucasrodrigues.pokemonshowcase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

abstract class BaseUnitTest : KoinTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    open val modules = listOf<Module>()

    @Before
    fun initializeDependencies() {
        startKoin {
            modules(modules)
        }
    }

    @After
    fun disposeDependencies() {
        stopKoin()
    }
}