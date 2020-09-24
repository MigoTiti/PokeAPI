package com.lucasrodrigues.pokemonshowcase.components

import android.content.SearchRecentSuggestionsProvider

class SuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.lucasrodrigues.pokemonshowcase.SuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}