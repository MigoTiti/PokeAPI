package com.lucasrodrigues.pokemonshowcase.framework.impl

import android.content.Context
import androidx.core.content.ContextCompat
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.framework.ResourceService

class ResourceServiceImpl(
    private val context: Context
) : ResourceService {

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }

    override fun getColor(id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    override fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            else -> exception.localizedMessage ?: exception.message
            ?: getString(R.string.error_unknown)
        }
    }
}
