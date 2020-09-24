package com.lucasrodrigues.pokemonshowcase.model

sealed class LoadingState {
    object Loading : LoadingState()

    object Idle : LoadingState()

    class Error(val error: Throwable) : LoadingState()
}