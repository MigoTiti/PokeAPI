package com.lucasrodrigues.pokemonshowcase.dependencies

import android.app.Activity
import com.lucasrodrigues.pokemonshowcase.framework.AlertService
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService
import com.lucasrodrigues.pokemonshowcase.framework.ResourceService
import com.lucasrodrigues.pokemonshowcase.framework.impl.AlertServiceImpl
import com.lucasrodrigues.pokemonshowcase.framework.impl.NavigationServiceImpl
import com.lucasrodrigues.pokemonshowcase.framework.impl.ResourceServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object FrameworkDependencies {
    val module = module(override = true) {
        single<ResourceService> { ResourceServiceImpl(androidContext().applicationContext) }

        factory<NavigationService> { (activity: Activity) -> NavigationServiceImpl(activity) }

        factory<AlertService> { (activity: Activity) ->
            AlertServiceImpl(
                resourceService = get(),
                activity = activity
            )
        }
    }
}