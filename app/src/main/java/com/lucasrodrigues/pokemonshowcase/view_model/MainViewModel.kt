package com.lucasrodrigues.pokemonshowcase.view_model

import androidx.paging.ExperimentalPagingApi
import com.lucasrodrigues.pokemonshowcase.framework.AlertService
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService

@ExperimentalPagingApi
class MainViewModel(
    navigationService: NavigationService,
    alertService: AlertService,
) : BaseViewModel(navigationService, alertService)